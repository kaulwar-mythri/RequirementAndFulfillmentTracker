package org.accolite.RequirementAndFulfillmentTracker.utils;

import org.accolite.RequirementAndFulfillmentTracker.entity.*;
import org.accolite.RequirementAndFulfillmentTracker.model.*;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EntityToDTO {
    public FulfillmentDTO getFulfillmentDTO(Fulfillment fulfillment) {
        return FulfillmentDTO.builder()
                .fulfillmentId(fulfillment.getFulfillmentId())
                .fulfillmentDate(fulfillment.getFulfillmentDate())
                .fulfillmentStatus(fulfillment.getFulfillmentStatus())
                .submission(getSubmissionDTO(fulfillment.getSubmission()))
                .build();
    }
    public SubmissionDTO getSubmissionDTO(Submission submission) {
        return SubmissionDTO.builder()
                .submissionId(submission.getSubmissionId())
                .submissionDate(submission.getSubmissionDate())
                .submissionStatus(submission.getSubmissionStatus())
                .feedback(submission.getFeedback())
                .benchCandidate(getBenchCandidateDTO(submission.getBenchCandidate()))
                .requirement(getRequirementDTO(submission.getRequirement()))
                .build();
    }
    public BenchCandidateDTO getBenchCandidateDTO(BenchCandidate candidate) {
        return BenchCandidateDTO.builder()
                .id(candidate.getId())
                .candidateName(candidate.getCandidateName())
                .skills(candidate.getSkills())
                .candidateStatus(candidate.getStatus())
                .benchManager(getUserRoleDTO(candidate.getBenchManager()))
                .benchPeriod(candidate.getBenchPeriod())
                .build();
    }

    public RequirementDTO getRequirementDTO(Requirement requirement) {
        AccountDTO requirement_accountDTO = getAccountDTO(requirement.getAccount());
        return RequirementDTO.builder()
                .requirementID(requirement.getRequirementId())
                .account(requirement_accountDTO)
                .requiredNo(requirement.getRequiredNo())
                .startDate(requirement.getStartDate())
                .endDate(requirement.getEndDate())
                .jobDescription(requirement.getJob_description())
                .hiringManager(requirement.getHiring_manager())
                .build();
    }

    public UserRoleDTO getUserRoleDTO(UserRole userRole) {
        Set<AccountDTO> accountDTOS = userRole.getAccounts().stream().map(this::getAccountDTO).collect(Collectors.toSet());

        return UserRoleDTO.builder()
                .id(userRole.getId())
                .role(userRole.getRole())
                .emailId(userRole.getEmailId())
                .accounts(accountDTOS)
                .build();
    }

    public AccountDTO getAccountDTO(Account account) {
        return AccountDTO.builder()
                .account_id(account.getAccount_id())
                .name(account.getName())
                .parentId(account.getParentId())
                .hierarchyTag(account.getHierarchyTag())
                .build();
    }
}
