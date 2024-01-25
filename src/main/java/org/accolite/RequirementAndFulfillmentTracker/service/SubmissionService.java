package org.accolite.RequirementAndFulfillmentTracker.service;
 import org.accolite.RequirementAndFulfillmentTracker.entity.Submission;
 import org.accolite.RequirementAndFulfillmentTracker.model.SubmissionDTO;
 import org.springframework.http.ResponseEntity;
 import org.springframework.stereotype.Service;

 import java.util.List;
 import java.util.Optional;

 @Service
public interface SubmissionService {
    ResponseEntity<SubmissionDTO> createSubmission(SubmissionDTO submission);

    ResponseEntity<List<SubmissionDTO>> getAllSubmissions();

     ResponseEntity<SubmissionDTO> updateSubmission(Long id, SubmissionDTO updatedSubmission);

//    void deleteSubmission(Long id);
}