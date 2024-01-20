package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import org.accolite.RequirementAndFulfillmentTracker.config.JWTService;
import org.accolite.RequirementAndFulfillmentTracker.entity.*;
import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.exception.UserUnauthorisedException;
import org.accolite.RequirementAndFulfillmentTracker.model.AccountDTO;
import org.accolite.RequirementAndFulfillmentTracker.model.FulfillmentDTO;
import org.accolite.RequirementAndFulfillmentTracker.model.SubmissionDTO;
import org.accolite.RequirementAndFulfillmentTracker.repository.AccountRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.FulfillmentRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.SubmissionRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.FulfillmentService;
import org.accolite.RequirementAndFulfillmentTracker.utils.EntityToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FulfillmentServiceImpl implements FulfillmentService {

    @Autowired
    FulfillmentRepository fulfillmentRepository;
    @Autowired
    SubmissionRepository submissionRepository;
    @Autowired
    JWTService jwtService;
    @Autowired
    EntityToDTO entityToDTO;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    AccountRepository accountRepository;

    List<Role> authorised_roles = new ArrayList<>(List.of(Role.REQUIREMENT_MANAGER, Role.BENCH_MANAGER, Role.ADMIN, Role.SUPER_ADMIN));

    @Override
    public ResponseEntity<FulfillmentDTO> createFulfillment(FulfillmentDTO fulfillment) {
        Submission submission = submissionRepository.findById(fulfillment.getSubmission().getSubmissionId()).orElseThrow(() -> new ResourceNotFoundException("Submission with given id is not found"));
        Fulfillment newFulfillment = Fulfillment.builder()
                .fulfillmentDate(fulfillment.getFulfillmentDate())
                .fulfillmentStatus(fulfillment.getFulfillmentStatus())
                .submission(submission)
                .build();
        newFulfillment = fulfillmentRepository.save(newFulfillment);
        return ResponseEntity.ok(entityToDTO.getFulfillmentDTO(newFulfillment));
    }

    @Override
    public ResponseEntity<List<FulfillmentDTO>> getAllFulfillments() {
        List<FulfillmentDTO> fulfillmentDTOS = fulfillmentRepository.findAll().stream().map(fulfillment -> {
            return entityToDTO.getFulfillmentDTO(fulfillment);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(fulfillmentDTOS);
    }

    @Override
    public ResponseEntity<FulfillmentDTO> getFulfillmentById(Long fulfillmentId) {
        FulfillmentDTO fulfillmentDTO =  entityToDTO.getFulfillmentDTO(fulfillmentRepository.findById(fulfillmentId).orElseThrow(() -> new ResourceNotFoundException("Fulfillment with given id not found")));
        return ResponseEntity.ok(fulfillmentDTO);
    }

    @Override
    public ResponseEntity<FulfillmentDTO> updateFulfillment(Long fulfillmentID, FulfillmentDTO updatedFulfillment) {
        Submission submission = submissionRepository.findById(updatedFulfillment.getSubmission().getSubmissionId()).orElseThrow(() -> new ResourceNotFoundException("Submission with given id not found"));
        Fulfillment existingFulfillment = fulfillmentRepository.findById(fulfillmentID)
                .orElseThrow(() -> new ResourceNotFoundException("Fulfillment not found with id: " + fulfillmentID));

        // Update fields of the existing fulfillment with the values from updatedFulfillment
        existingFulfillment.setFulfillmentDate(updatedFulfillment.getFulfillmentDate());
        existingFulfillment.setFulfillmentStatus(updatedFulfillment.getFulfillmentStatus());
        existingFulfillment.setSubmission(submission);

        // Save the updated fulfillment to the database
        existingFulfillment = fulfillmentRepository.save(existingFulfillment);
        return ResponseEntity.ok(entityToDTO.getFulfillmentDTO(existingFulfillment));
    }

    @Override
    public void deleteFulfillment(Long fulfillmentId) {
        fulfillmentRepository.deleteById(fulfillmentId);
    }

    private void checkIfAuthorized(AccountDTO requirement_account) {
        UserRole user = userRoleRepository.findByEmailId(jwtService.getUser()).orElse(null);
        Account requirementBU = accountRepository.findById(requirement_account.getParentId()).orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        List<Account> userBUs = user.getAccounts().stream().filter(account -> {
            return account.getHierarchyTag() == HierarchyTag.BUSINESS_UNIT;
        }).collect(Collectors.toList());

        if(!authorised_roles.contains(user.getRole()) || !userBUs.contains(requirementBU)) {
            throw new UserUnauthorisedException("User is not authorised to perform the above operation");
        }
    }
}
