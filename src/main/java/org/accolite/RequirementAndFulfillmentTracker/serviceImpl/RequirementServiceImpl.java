package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

<<<<<<< HEAD

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.config.JWTService;
import org.accolite.RequirementAndFulfillmentTracker.entity.*;
import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.exception.UserUnauthorisedException;
import org.accolite.RequirementAndFulfillmentTracker.model.AccountDTO;
import org.accolite.RequirementAndFulfillmentTracker.model.RequirementDTO;
import org.accolite.RequirementAndFulfillmentTracker.repository.AccountRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.RequirementRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.EmailNotificationService;
import org.accolite.RequirementAndFulfillmentTracker.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
=======
import org.accolite.RequirementAndFulfillmentTracker.entity.Requirement;
import org.accolite.RequirementAndFulfillmentTracker.repository.RequirementRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> origin/alisimran
import org.springframework.stereotype.Service;
import org.accolite.RequirementAndFulfillmentTracker.utils.EntityToDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RequirementServiceImpl implements RequirementService {

    @Autowired
    private RequirementRepository requirementsRepository;
<<<<<<< HEAD
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private EmailNotificationService emailNotificationService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private EntityToDTO entityToDTO;

    List<Role> authorised_roles = new ArrayList<>(List.of(Role.REQUIREMENT_MANAGER, Role.ADMIN, Role.SUPER_ADMIN));
=======

>>>>>>> origin/alisimran
    @Override
    public ResponseEntity<RequirementDTO> createRequirement(RequirementDTO requirement) {
        checkIfAuthorized(requirement.getAccount());
        Account account = accountRepository.findById(requirement.getAccount().getAccount_id()).orElse(null);
        Requirement newRequirement = Requirement.builder()
                .startDate(requirement.getStartDate())
                .endDate(requirement.getEndDate())
                .requiredNo(requirement.getRequiredNo())
                .job_description(requirement.getJobDescription())
                .hiring_manager(requirement.getHiringManager())
                .account(account)
//                .skillSet(requirement.getSkillSet())
                .build();
        newRequirement = requirementsRepository.save(newRequirement);
        return ResponseEntity.ok(entityToDTO.getRequirementDTO(newRequirement));
    }

    @Override
<<<<<<< HEAD
    public ResponseEntity<RequirementDTO> updateRequirement(Long id, RequirementDTO updatedRequirement) {
        checkIfAuthorized(updatedRequirement.getAccount());
        Requirement existingRequirement = requirementsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Requirement with given ID not found"));

        existingRequirement.setStartDate(updatedRequirement.getStartDate());
        existingRequirement.setEndDate(updatedRequirement.getEndDate());
        existingRequirement.setRequiredNo(updatedRequirement.getRequiredNo());
//        existingRequirement.(updatedRequirement.getSkillSet());
        existingRequirement.setJob_description(updatedRequirement.getJobDescription());
        existingRequirement.setHiring_manager(updatedRequirement.getHiringManager());

        existingRequirement = requirementsRepository.save(existingRequirement);

        return ResponseEntity.ok(entityToDTO.getRequirementDTO(existingRequirement));
=======
    public Requirement updateRequirement(Long id, Requirement updatedRequirement) {
        Optional<Requirement> existingRequirement = requirementsRepository.findById(id);
        if (existingRequirement.isPresent()) {
            Requirement requirementToUpdate = existingRequirement.get();
            // Update fields based on your needs
            requirementToUpdate.setStartDate(updatedRequirement.getStartDate());
            requirementToUpdate.setEndDate(updatedRequirement.getEndDate());
            requirementToUpdate.setRequiredNo(updatedRequirement.getRequiredNo());
            requirementToUpdate.setSkillSet(updatedRequirement.getSkillSet());
            requirementToUpdate.setJob_description(updatedRequirement.getJob_description());
            requirementToUpdate.setHiring_manager(updatedRequirement.getHiring_manager());
>>>>>>> origin/alisimran

    }
    @Override
    public ResponseEntity<List<RequirementDTO>> getAllRequirements() {
        List<RequirementDTO> requirementDTOS =  requirementsRepository.findAll().stream().map(requirement -> {
            return entityToDTO.getRequirementDTO(requirement);
        }).collect(Collectors.toList());
        return ResponseEntity.ok(requirementDTOS);
    }

    @Override
    public ResponseEntity<RequirementDTO> getRequirementById(Long id) {
        Requirement requirement = requirementsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Requirement not found with id: " + id));
        return ResponseEntity.ok(entityToDTO.getRequirementDTO(requirement));
    }


    @Override
    public void deleteRequirement(Long id) {
        Requirement existingRequirement = requirementsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Requirement with id" + id + "not found"));
        RequirementDTO requirementDTO = entityToDTO.getRequirementDTO(existingRequirement);
        checkIfAuthorized(requirementDTO.getAccount());

        existingRequirement.setAccount(null);
        requirementsRepository.deleteById(id);
    }

    @Override
<<<<<<< HEAD
    public void alertBench(Long benchManagerId, Set<Long> requirementIds) throws MessagingException {
        List<Requirement> requirements = requirementsRepository.findAllById(requirementIds);
        // using the given id , get the benchmanger's email id and pass it through

//        UserRole benchManager = userRoleRepository.findById(benchManagerId).orElse(null);
//        if(benchManager == null) {
//            System.out.println("bench manager doesn't exist");
//            return;
//        }
//        String benchManagerEmail =benchManager.getEmailId();
        // we will have to set username and apppassword of the respective benchmanager in application.properties
        // this needs to be implemented, tested and reviewed
        if (!requirements.isEmpty()) {
            String tableContent = generateRequirementTable(requirements);
            emailNotificationService.sendNotification("raftspringto@gmail.com", "Requirements Obtained", tableContent);
        }
        return;
=======
    public void alertBench(Long benchManagerId, Set<Long> requirementIds) {
>>>>>>> origin/alisimran
        // Implement alertBench logic based on your requirements
        // You can perform actions like notifying the bench manager or updating the database
//
//        // Retrieve bench manager information (this is just a placeholder)
//        BenchManager benchManager = getBenchManagerById(benchManagerId);
//
//        // Get the requirements based on the provided IDs
//        List<Requirement> requirements = requirementsRepository.findAllById(requirementIds);
//
//        // Notify the bench manager (this is just a placeholder, replace with your actual notification mechanism)
//        sendNotificationToBenchManager(benchManager, requirements);

    }

//    private BenchManager getBenchManagerById(Long benchManagerId) {
//        // Retrieve bench manager information from your data source (database, API, etc.)
//        // This is a placeholder method; replace it with your actual data retrieval logic
//        return new BenchManager(benchManagerId, "John Doe", "john.doe@example.com");
//    }

//    private void sendNotificationToBenchManager(BenchManager benchManager, List<Requirement> requirements) {
//        // Placeholder for sending a notification
//        // Replace with your actual notification mechanism (email, messaging service, etc.)
//        System.out.println("Notifying Bench Manager " + benchManager.getName() +
//                " (" + benchManager.getEmail() + ") about the following requirements: " + requirements);
//    }

    @Override
    public void alertHiring(Long hiringManagerId, Set<Long> requirementIds) {
        // Implement alertHiring logic based on your requirements
        // You can perform actions like notifying the hiring manager or updating the database
    }

    //requirement_account is
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