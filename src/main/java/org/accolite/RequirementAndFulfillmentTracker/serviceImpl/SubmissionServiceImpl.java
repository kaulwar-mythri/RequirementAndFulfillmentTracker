package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;
        import jakarta.persistence.EntityNotFoundException;

        import org.accolite.RequirementAndFulfillmentTracker.config.JWTService;
        import org.accolite.RequirementAndFulfillmentTracker.entity.*;
        import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
        import org.accolite.RequirementAndFulfillmentTracker.exception.UserUnauthorisedException;
        import org.accolite.RequirementAndFulfillmentTracker.model.AccountDTO;
        import org.accolite.RequirementAndFulfillmentTracker.model.BenchCandidateDTO;
        import org.accolite.RequirementAndFulfillmentTracker.model.RequirementDTO;
        import org.accolite.RequirementAndFulfillmentTracker.model.SubmissionDTO;
        import org.accolite.RequirementAndFulfillmentTracker.repository.*;
        import org.accolite.RequirementAndFulfillmentTracker.service.SubmissionService;
        import org.accolite.RequirementAndFulfillmentTracker.utils.EntityToDTO;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Optional;
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
        Requirement requirement = requirementRepository.findById(submission.getRequirement().getRequirementID()).orElseThrow(() -> new ResourceNotFoundException("Requirement with given id not found"));
        BenchCandidate benchCandidate = benchCandidateRepository.findById(submission.getBenchCandidate().getId()).orElseThrow(() -> new ResourceNotFoundException("Bench candidate with given id not found"));
        Submission newSubmission = Submission.builder()
                .submissionDate(submission.getSubmissionDate())
                .submissionStatus(submission.getSubmissionStatus())
                .requirement(requirement)
                .benchCandidate(benchCandidate)
                .feedback(submission.getFeedback())
                .build();
        newSubmission = submissionRepository.save(newSubmission);
        return ResponseEntity.ok(entityToDTO.getSubmissionDTO(newSubmission));
    }

    @Override
    public ResponseEntity<List<SubmissionDTO>> getAllSubmissions() {
         List<SubmissionDTO> submissionDTOList = submissionRepository.findAll().stream().map(submission -> {
            return entityToDTO.getSubmissionDTO(submission);
        }).collect(Collectors.toList());

         return ResponseEntity.ok(submissionDTOList);
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
        Requirement requirement = requirementRepository.findById(updatedSubmission.getRequirement().getRequirementID()).orElseThrow(() -> new ResourceNotFoundException("Requirement with given id not found"));
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
        submissionRepository.deleteById(id);
    }

    private void checkIfAuthorized(AccountDTO requirement_account) {
        UserRole user = userRoleRepository.findByEmailId(jwtService.getUser()).orElse(null);
        Account requirementBU = accountRepository.findById(requirement_account.getParentId()).orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        List<Account> userBUs = user.getAccounts().stream().filter(account -> {
            return account.getHierarchyTag() == HierarchyTag.BUSINESS_UNIT;
        }).collect(Collectors.toList());

        if(!userBUs.contains(requirementBU)) {
            throw new UserUnauthorisedException("User is not authorised to perform the above operation");
        }
    }

}



