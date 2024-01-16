package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;
        import jakarta.persistence.EntityNotFoundException;

        import org.accolite.RequirementAndFulfillmentTracker.repository.SubmissionRepository;
        import org.accolite.RequirementAndFulfillmentTracker.entity.Submission;
        import org.accolite.RequirementAndFulfillmentTracker.service.SubmissionService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.stereotype.Service;

        import java.util.List;
        import java.util.Optional;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;
    @Override
    public Submission createSubmission(Submission submission) {
        return (Submission) submissionRepository.save(submission);
    }

    @Override
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public Optional<Submission> getSubmissionById(Long id) {
        return submissionRepository.findById(id);
    }

    @Override
    public Submission updateSubmission(Long id, Submission updatedSubmission) {

        Optional<Submission> optionalExistingSubmission = submissionRepository.findById(id);

        if (optionalExistingSubmission.isPresent()) {
            Submission  existingSubmission = optionalExistingSubmission.get();
            existingSubmission.setSubmissionId(updatedSubmission.getSubmissionId());
            existingSubmission.setSubmissionDate(updatedSubmission.getSubmissionDate());
            existingSubmission.setBenchCandidate(updatedSubmission.getBenchCandidate());
            existingSubmission.setSubmissionStatus(updatedSubmission.getSubmissionStatus());
            existingSubmission.setFeedback(updatedSubmission.getFeedback());
           existingSubmission.setRequirement(updatedSubmission.getRequirement());

            return (Submission) submissionRepository.save(existingSubmission);
        } else {
            throw new EntityNotFoundException("BenchCandidate with ID " + id + " not found.");
        }

    }

    @Override
    public void deleteSubmission(Long id) {
        submissionRepository.deleteById(id);
    }
}



