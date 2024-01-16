package org.accolite.RequirementAndFulfillmentTracker.service;
 import org.accolite.RequirementAndFulfillmentTracker.entity.Submission;
 import org.springframework.stereotype.Service;

 import java.util.List;
 import java.util.Optional;

 @Service
public interface SubmissionService {
    Submission createSubmission(Submission submission);

    List<Submission> getAllSubmissions();

    Optional<Submission> getSubmissionById(Long id);

    Submission updateSubmission(Long id, Submission updatedSubmission);

    void deleteSubmission(Long id);
}