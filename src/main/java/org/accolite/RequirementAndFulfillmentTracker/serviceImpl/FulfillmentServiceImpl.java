package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import org.accolite.RequirementAndFulfillmentTracker.config.JWTService;
import org.accolite.RequirementAndFulfillmentTracker.entity.*;
import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.exception.UserUnauthorisedException;
import org.accolite.RequirementAndFulfillmentTracker.model.*;
import org.accolite.RequirementAndFulfillmentTracker.repository.*;
import org.accolite.RequirementAndFulfillmentTracker.service.FulfillmentService;
import org.accolite.RequirementAndFulfillmentTracker.utils.EntityToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FulfillmentServiceImpl implements FulfillmentService {

    @Autowired
    FulfillmentRepository fulfillmentRepository;
    @Autowired
    SubmissionRepository submissionRepository;
    @Autowired
    BenchCandidateRepository benchCandidateRepository;
    @Autowired
    RequirementRepository requirementRepository ;
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
        BenchCandidate benchCandidate = benchCandidateRepository.findById(entityToDTO.getSubmissionDTO(submission).getBenchCandidate().getId()).orElseThrow(() -> new ResourceNotFoundException("Bench Candidate with given id is not found"));
        if(benchCandidate.getStatus() == CandidateStatus.SELECTED) throw new ResourceNotFoundException("Bench Candidate was already selected by another account");

        Fulfillment newFulfillment = Fulfillment.builder()
                .fulfillmentDate(fulfillment.getFulfillmentDate())
                .fulfillmentStatus(fulfillment.getFulfillmentStatus())
                .submission(submission)
                .build();
        newFulfillment = fulfillmentRepository.save(newFulfillment);

        submission.setSubmissionStatus(SubmissionStatus.ACCEPTED);
        benchCandidate.setStatus(CandidateStatus.SELECTED);
        updateRequirement(entityToDTO.getSubmissionDTO(submission));
        return ResponseEntity.ok(entityToDTO.getFulfillmentDTO(newFulfillment));

    }


    @Override
    public ResponseEntity<List<FulfillmentDTO>> getAllFulfillments() {
        UserRoleDTO userRole = entityToDTO.getUserRoleDTO(userRoleRepository.findByEmailId(jwtService.getUser())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
          Role user_role = userRoleRepository.findByEmailId(jwtService.getUser()).orElseThrow(() -> new ResourceNotFoundException("User Not found")).getRole();
        if(user_role == Role.ADMIN || user_role == Role.SUPER_ADMIN) {
            return ResponseEntity.ok(fulfillmentRepository.findAll().stream().map(fulfillment -> {
                return entityToDTO.getFulfillmentDTO(fulfillment);
            }).toList());
        }
        //user_accounts -> storing all accounts of least level
        Set<Account> user_accounts = userRole.getAccounts().stream().map(accountDTO -> {
            return accountRepository.findById(accountDTO.getAccount_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        }).collect(Collectors.toSet());

        List<FulfillmentDTO> fulfillmentDTOS =  fulfillmentRepository.findAll().stream().map(fulfillment -> {
            return entityToDTO.getFulfillmentDTO(fulfillment);
        }).filter(fulfillmentDTO -> {
            Account requirementAccount = accountRepository.findById(fulfillmentDTO.getSubmission().getRequirement().getAccount().getAccount_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

            if(requirementAccount.getHierarchyTag() == HierarchyTag.BUSINESS_UNIT && user_accounts.contains(requirementAccount))
                return true;
            else if(requirementAccount.getHierarchyTag() == HierarchyTag.CLIENT && (user_accounts.contains(requirementAccount) || user_accounts.contains(entityToDTO.getAccountDTO(accountRepository.findById(requirementAccount.getParentId()).orElse(null)))))
                return true;
            else if(requirementAccount.getHierarchyTag() == HierarchyTag.DEPARTMENT &&
                    (user_accounts.contains(requirementAccount)
                            || user_accounts.contains(entityToDTO.getAccountDTO(accountRepository.findById(requirementAccount.getParentId()).orElse(null)))
                            || user_accounts.contains(entityToDTO.getAccountDTO(accountRepository.findById(accountRepository.findById(requirementAccount.getParentId()).orElse(null).getParentId()).orElse(null)))))
                return true;

            return false;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(fulfillmentDTOS);
    }
    @Override
    public ResponseEntity<FulfillmentDTO> updateFulfillment(Long fulfillmentID, FulfillmentDTO updatedFulfillment) {
        Submission submission = submissionRepository.findById(updatedFulfillment.getSubmission().getSubmissionId())
                .orElseThrow(() -> new ResourceNotFoundException("Submission with given id not found"));
        BenchCandidate benchCandidate = benchCandidateRepository.findById(updatedFulfillment.getSubmission().getBenchCandidate().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Bench Candidate is not found"));
        Fulfillment existingFulfillment = fulfillmentRepository.findById(fulfillmentID)
                .orElseThrow(() -> new ResourceNotFoundException("Fulfillment not found with id: " + fulfillmentID));

        // Just Update fields of the existing fulfillment with the values from updatedFulfillment
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

    private void checkIfAuthorized(AccountDTO requirement_accountDTO) {
        UserRole user = userRoleRepository.findByEmailId(jwtService.getUser()).orElse(null);

        if(user.getRole() == Role.SUPER_ADMIN)
            return;

        Set<Account> user_accounts = user.getAccounts().stream().map(accountDTO -> {
            return accountRepository.findById(accountDTO.getAccount_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        }).collect(Collectors.toSet());

        Account requirementAccount = accountRepository.findById(requirement_accountDTO.getAccount_id())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        if(!authorised_roles.contains(user.getRole()) || (requirementAccount.getHierarchyTag() == HierarchyTag.BUSINESS_UNIT && !user_accounts.contains(requirementAccount)))
            throw new UserUnauthorisedException("User is not authorised to perform the above operation");
        else if(!authorised_roles.contains(user.getRole()) || (requirementAccount.getHierarchyTag() == HierarchyTag.CLIENT && !(user_accounts.contains(requirementAccount) || user_accounts.contains(entityToDTO.getAccountDTO(accountRepository.findById(requirementAccount.getParentId()).orElse(null))))))
            throw new UserUnauthorisedException("User is not authorised to perform the above operation");
        else if(!authorised_roles.contains(user.getRole()) || (requirementAccount.getHierarchyTag() == HierarchyTag.DEPARTMENT &&
                !(user_accounts.contains(requirementAccount)
                        || user_accounts.contains(entityToDTO.getAccountDTO(accountRepository.findById(requirementAccount.getParentId()).orElse(null)))
                        || user_accounts.contains(entityToDTO.getAccountDTO(accountRepository.findById(accountRepository.findById(requirementAccount.getParentId()).orElse(null).getParentId()).orElse(null)))
                )))
            throw new UserUnauthorisedException("User is not authorised to perform the above operation");
    }

    private void updateRequirement(SubmissionDTO submission) {
        Requirement requirement = requirementRepository.findById(submission.getRequirement().getRequirementId()).orElseThrow(() -> new ResourceNotFoundException("Requirement not found"));

        requirement.setFulfilledNo(requirement.getFulfilledNo() + 1);
        requirementRepository.save(requirement);
    }
}
