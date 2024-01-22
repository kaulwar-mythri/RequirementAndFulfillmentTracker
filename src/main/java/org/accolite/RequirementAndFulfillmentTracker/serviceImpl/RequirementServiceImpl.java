package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.config.JWTService;
import org.accolite.RequirementAndFulfillmentTracker.entity.*;
import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.exception.UserUnauthorisedException;
import org.accolite.RequirementAndFulfillmentTracker.model.AccountDTO;
import org.accolite.RequirementAndFulfillmentTracker.model.RequirementDTO;
import org.accolite.RequirementAndFulfillmentTracker.model.UserRoleDTO;
import org.accolite.RequirementAndFulfillmentTracker.repository.AccountRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.RequirementRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.EmailNotificationService;
import org.accolite.RequirementAndFulfillmentTracker.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;

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

    @Override
    public ResponseEntity<RequirementDTO> createRequirement(RequirementDTO requirement) {
        checkIfAuthorized(requirement.getAccount());
        Account account = accountRepository.findById(requirement.getAccount().getAccount_id()).orElse(null);
        Requirement newRequirement = Requirement.builder()
                .startDate(requirement.getStartDate())
                .endDate(requirement.getEndDate())
                .requiredNo(requirement.getRequiredNo())
                .fulfilledNo(requirement.getFulfilledNo())
                .job_description(requirement.getJobDescription())
                .hiring_manager(requirement.getHiringManager())
                .account(account)
//                .skillSet(requirement.getSkillSet())
                .build();
        newRequirement = requirementsRepository.save(newRequirement);
        return ResponseEntity.ok(entityToDTO.getRequirementDTO(newRequirement));
    }

    @Override

    public ResponseEntity<RequirementDTO> updateRequirement(Long id, RequirementDTO updatedRequirement) {
        checkIfAuthorized(updatedRequirement.getAccount());
        Requirement existingRequirement = requirementsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Requirement with given ID not found"));

        existingRequirement.setStartDate(updatedRequirement.getStartDate());
        existingRequirement.setEndDate(updatedRequirement.getEndDate());
        existingRequirement.setRequiredNo(updatedRequirement.getRequiredNo());
        existingRequirement.setFulfilledNo(updatedRequirement.getFulfilledNo());
//        existingRequirement.(updatedRequirement.getSkillSet());
        existingRequirement.setJob_description(updatedRequirement.getJobDescription());
        existingRequirement.setHiring_manager(updatedRequirement.getHiringManager());

        existingRequirement = requirementsRepository.save(existingRequirement);

        return ResponseEntity.ok(entityToDTO.getRequirementDTO(existingRequirement));


    }

//    public ResponseEntity<List<RequirementDTO>> getAllRequirements() {
//        List<RequirementDTO> requirementDTOS =  requirementsRepository.findAll().stream().map(requirement -> {
//            return entityToDTO.getRequirementDTO(requirement);
//        }).collect(Collectors.toList());
//        return ResponseEntity.ok(requirementDTOS);
//    }
    @Override
    public ResponseEntity<List<RequirementDTO>> getAllRequirements() {
        UserRoleDTO userRole = entityToDTO.getUserRoleDTO(userRoleRepository.findByEmailId(jwtService.getUser()).orElseThrow(() -> new ResourceNotFoundException("User nt found")));
        List<AccountDTO> user_BUaccounts = userRole.getAccounts().stream().map(accountDTO -> {
            return entityToDTO.getAccountDTO(accountRepository.findById(accountDTO.getParentId()).orElseThrow(() -> new ResourceNotFoundException("Accpunt not found")));
        }).collect(Collectors.toList());

        List<RequirementDTO> requirementDTOS =  requirementsRepository.findAll().stream().map(requirement -> {
            return entityToDTO.getRequirementDTO(requirement);
        }).filter(requirementDTO -> {
            AccountDTO requirementBU = entityToDTO.getAccountDTO(accountRepository.findById(requirementDTO.getAccount().getParentId()).orElseThrow(() -> new ResourceNotFoundException("Account not found")));
            return user_BUaccounts.contains(requirementBU);
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


    }



    private String generateRequirementTable(List<Requirement> requirements) {

        StringBuilder tableContent = new StringBuilder("<table border='1'><tr><th>Start Date</th><th>End Date</th><th>Required Number</th><th>Job Description</th><th>Hiring Manager</th></tr>");
        /*
        *   <html>
<head></head>
<body>
<!-- Your table content here -->
</body>
</html>
        * */
//        tableContent.append("<html>");
//        tableContent.append("<head>");
//        tableContent.append("</head>");
//        tableContent.append("<body>");

        for (Requirement requirement : requirements) {
            tableContent.append("<tr>")
                    .append("<td>").append(requirement.getStartDate()).append("</td>")
                    .append("<td>").append(requirement.getEndDate()).append("</td>")
                    .append("<td>").append(requirement.getRequiredNo()).append("</td>")
                    .append("<td>").append(requirement.getJob_description()).append("</td>")
                    .append("<td>").append(requirement.getHiring_manager()).append("</td>")
                    .append("</tr>");
        }

        tableContent.append("</table>");

        tableContent.append("</body>");
        tableContent.append("</html>");

        return tableContent.toString();
    }

    @Override
    public void alertHiring(Long hiringManagerId, Set<Long> requirementIds) {
        // Implement alertHiring logic based on your requirements
        // You can perform actions like notifying the hiring manager or updating the database
    }

    //requirement_account is
    // this is wrong, the second condition is giving false
    private void checkIfAuthorized(AccountDTO requirement_account) {
        UserRole user = userRoleRepository.findByEmailId(jwtService.getUser()).orElse(null);
        Account requirementBU = accountRepository.findById(requirement_account.getParentId()).orElseThrow(() -> new ResourceNotFoundException("Account not found"));

//        List<Account> userBUs = user.getAccounts().stream().map(account -> {
//            Account parentAccount = accountRepository.findById(account.getParentId()).orElse(null);
//            return account.getHierarchyTag() == HierarchyTag.BUSINESS_UNIT;
//
//        }).collect(Collectors.toList());
        List<Account> userBUs = user.getAccounts().stream().map(account -> {
            return accountRepository.findById(account.getParentId()).orElse(null);
        }).collect(Collectors.toList());

        if(!authorised_roles.contains(user.getRole()) || !userBUs.contains(requirementBU)) {
            throw new UserUnauthorisedException("User is not authorised to perform the above operation");
        }
    }
}