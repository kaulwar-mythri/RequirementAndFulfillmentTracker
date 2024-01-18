package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;


import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.Requirement;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.repository.AccountRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.RequirementRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.EmailNotificationService;
import org.accolite.RequirementAndFulfillmentTracker.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;


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
    @Override
    public Requirement createRequirement(Requirement requirement) {
        System.out.println(requirement);


//        Account account = requirement.getAccount();
//        Set<UserRole> userRoles = account.getUserRoles();
//        System.out.printf("userroles " + userRoles);
//        Account newAccount = Account.builder()
//                .name(account.getName())
//                .hierarchyTag(account.getHierarchyTag())
//                .parentId(account.getParentId())
//                .userRoles(account.getUserRoles())
//                .build();
//        Account account = accountRepository.findById()
//        System.out.println(accountRepository.save(newAccount));
        Account account = accountRepository.findById(requirement.getAccount().getAccount_id()).orElse(null);
        Requirement newRequirement = Requirement.builder()
                .startDate(requirement.getStartDate())
                .endDate(requirement.getEndDate())
                .requiredNo(requirement.getRequiredNo())
                .job_description(requirement.getJob_description())
                .hiring_manager(requirement.getHiring_manager())
                .account(account)
                .build();
        return requirementsRepository.save(newRequirement);
    }

    @Override
    public Requirement updateRequirement(Long id, Requirement updatedRequirement) {
        Optional<Requirement> existingRequirement = requirementsRepository.findById(id);
        if (existingRequirement.isPresent()) {
            Requirement requirementToUpdate = existingRequirement.get();
            // Update fields based on your needs
            requirementToUpdate.setStartDate(updatedRequirement.getStartDate());
            requirementToUpdate.setEndDate(updatedRequirement.getEndDate());
            requirementToUpdate.setRequiredNo(updatedRequirement.getRequiredNo());
//            requirementToUpdate.setSkillSet(updatedRequirement.getSkillSet());
            requirementToUpdate.setJob_description(updatedRequirement.getJob_description());
            requirementToUpdate.setHiring_manager(updatedRequirement.getHiring_manager());

            return requirementsRepository.save(requirementToUpdate);
        } else {
            // Handle case where the requirement with the given id is not found
            throw new IllegalArgumentException("Requirement not found with id: " + id);
        }
    }
    @Override
    public List<Requirement> getAllRequirements() {
        return requirementsRepository.findAll();
    }

    @Override
    public Requirement getRequirementById(Long id) {
        return requirementsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Requirement not found with id: " + id));
    }


    @Override
    public void deleteRequirement(Long id) {
        // retrieve the existing requirment
        Requirement existingRequirement = requirementsRepository.findById(id).orElse(null);
        // Get the account
        if(existingRequirement == null){
            System.out.println("Requirement doesn't exits ");
            return;
        }
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
        return;
        // Implement alertBench logic based on your requirements
        // You can perform actions like notifying the bench manager or updating the database

        // Retrieve bench manager information (this is just a placeholder)
//        BenchManager benchManager = getBenchManagerById(benchManagerId);
//
//        // Get the requirements based on the provided IDs
//        List<Requirement> requirements = requirementsRepository.findAllById(requirementIds);
//
//        // Notify the bench manager (this is just a placeholder, replace with your actual notification mechanism)
//        sendNotificationToBenchManager(benchManager, requirements);

    }

    // method to generate requirements table from a given list of requirement Ids

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
//   }

    @Override
    public void alertHiring(Long hiringManagerId, Set<Long> requirementIds) {
        // Implement alertHiring logic based on your requirements
        // You can perform actions like notifying the hiring manager or updating the database
    }
}