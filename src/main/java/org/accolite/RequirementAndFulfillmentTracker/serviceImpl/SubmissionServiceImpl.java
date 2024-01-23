package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;
        import jakarta.persistence.EntityNotFoundException;

        import org.accolite.RequirementAndFulfillmentTracker.config.JWTService;
        import org.accolite.RequirementAndFulfillmentTracker.entity.*;
        import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceInUseException;
        import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
        import org.accolite.RequirementAndFulfillmentTracker.exception.UserUnauthorisedException;
        import org.accolite.RequirementAndFulfillmentTracker.model.*;
        import org.accolite.RequirementAndFulfillmentTracker.repository.*;
        import org.accolite.RequirementAndFulfillmentTracker.service.SubmissionService;
        import org.accolite.RequirementAndFulfillmentTracker.utils.EntityToDTO;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Optional;
        import java.util.Set;
        import java.util.stream.Collectors;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;
    @Autowired
    private RequirementRepository requirementRepository;
    @Autowired
    private BenchCandidateRepository benchCandidateRepository;
    @Autowired
    private EntityToDTO entityToDTO;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JWTService jwtService;
      //discuss
    @Override
    public ResponseEntity<SubmissionDTO> createSubmission(SubmissionDTO submission) {
        checkIfAuthorized(submission.getRequirement().getAccount());
        /*  1. when adding a new submission , check if the submission status is SELECTED then update the candidate status in the candidate bench
         *  2. also duplicate entries not handled, it keeps adding -> instead of auto generated Ids replace by user employee id -> which would be passed
         *   candidate status , and bench startDate and endDate
         * */
        Requirement requirement = requirementRepository.findById(submission.getRequirement().getRequirementId()).orElseThrow(() -> new ResourceNotFoundException("Requirement with given id not found"));
        BenchCandidate benchCandidate = benchCandidateRepository.findById(submission.getBenchCandidate().getId()).orElseThrow(() -> new ResourceNotFoundException("Bench candidate with given id not found"));
        if(benchCandidate.getStatus() == CandidateStatus.HOLD || benchCandidate.getStatus() == CandidateStatus.SELECTED) throw new ResourceInUseException("Candidate with given id was either submitted for another client interview or selected by another client");

        Submission newSubmission = Submission.builder()
                .submissionDate(submission.getSubmissionDate())
                .submissionStatus(submission.getSubmissionStatus())
                .requirement(requirement)
                .benchCandidate(benchCandidate)
                .feedback(submission.getFeedback())
                .build();
        newSubmission = submissionRepository.save(newSubmission);

        benchCandidate.setStatus(CandidateStatus.HOLD); benchCandidateRepository.save(benchCandidate);
        return ResponseEntity.ok(entityToDTO.getSubmissionDTO(newSubmission));
    }

    // update the candidate status if they are selected, bench manager can then update the status accordingly
    // for eg if bg check is going on or onboarding
    public void updateCandidateStatusIfSelected(BenchCandidate benchCandidate){
        benchCandidate.setStatus(CandidateStatus.SELECTED);
        benchCandidateRepository.save(benchCandidate);
    }

    @Override
    public ResponseEntity<List<SubmissionDTO>> getAllSubmissions() {
        UserRoleDTO userRole = entityToDTO.getUserRoleDTO(userRoleRepository.findByEmailId(jwtService.getUser())
                .orElseThrow(() -> new ResourceNotFoundException("User nt found")));

        //user_accounts -> storing all accounts of least level
        Set<AccountDTO> user_accounts = userRole.getAccounts().stream().map(accountDTO -> {
            return entityToDTO.getAccountDTO(accountRepository.findById(accountDTO.getAccount_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found")));
        }).collect(Collectors.toSet());

        List<SubmissionDTO> submissionDTOS =  submissionRepository.findAll().stream().map(submission -> {
            return entityToDTO.getSubmissionDTO(submission);
        }).filter(submissionDTO -> {
            AccountDTO requirementAccount = entityToDTO.getAccountDTO(accountRepository.findById(submissionDTO.getRequirement().getAccount().getAccount_id())
                    .orElseThrow(() -> new ResourceNotFoundException("Account not found")));

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
        return ResponseEntity.ok(submissionDTOS);
    }

    @Override
    public ResponseEntity<SubmissionDTO> getSubmissionById(Long id) {
        SubmissionDTO submissionDTO =  entityToDTO.getSubmissionDTO(submissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission with given id not found")));
        return ResponseEntity.ok(submissionDTO);
    }

    @Override
    public ResponseEntity<SubmissionDTO> updateSubmission(Long id, SubmissionDTO updatedSubmission) {
        checkIfAuthorized(updatedSubmission.getRequirement().getAccount());
        BenchCandidate benchCandidate = benchCandidateRepository.findById(updatedSubmission.getBenchCandidate().getId()).orElseThrow(() -> new ResourceNotFoundException("Bench Candidate with given id not found"));
        Requirement requirement = requirementRepository.findById(updatedSubmission.getRequirement().getRequirementId()).orElseThrow(() -> new ResourceNotFoundException("Requirement with given id not found"));
        Submission existingSubmission = submissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission with given id not found"));

        existingSubmission.setSubmissionId(updatedSubmission.getSubmissionId());
        existingSubmission.setSubmissionDate(updatedSubmission.getSubmissionDate());
        existingSubmission.setBenchCandidate(benchCandidate);
        existingSubmission.setSubmissionStatus(updatedSubmission.getSubmissionStatus());
        existingSubmission.setFeedback(updatedSubmission.getFeedback());
        existingSubmission.setRequirement(requirement);

        existingSubmission = submissionRepository.save(existingSubmission);
        return ResponseEntity.ok(entityToDTO.getSubmissionDTO(existingSubmission));
    }

    @Override
    public void deleteSubmission(Long id) {
        Submission submission = submissionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Submission with given id not found"));
        AccountDTO accountDTO = entityToDTO.getAccountDTO(submission.getRequirement().getAccount());
        checkIfAuthorized(accountDTO);
        // foreign key not set null hence not working
        submission.setBenchCandidate(null);
        submission.setRequirement(null);

        submissionRepository.deleteById(id);
    }

    private void checkIfAuthorized(AccountDTO requirement_account) {
        UserRole user = userRoleRepository.findByEmailId(jwtService.getUser()).orElse(null);
        AccountDTO requirementAccount = entityToDTO.getAccountDTO(accountRepository.findById(requirement_account.getAccount_id())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found")));

        List<Account> userBUs = user.getAccounts().stream().map(account -> {
            return accountRepository.findById(account.getParentId()).orElse(null);
        }).collect(Collectors.toList());
        Set<AccountDTO> user_accounts = entityToDTO.getUserRoleDTO(user).getAccounts();

        if(!user_accounts.contains(requirementAccount)) {
            throw new UserUnauthorisedException("User is not authorised to perform the above operation");
        }
    }

}



