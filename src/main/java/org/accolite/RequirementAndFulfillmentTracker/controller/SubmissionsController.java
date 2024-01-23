package org.accolite.RequirementAndFulfillmentTracker.controller;
import org.accolite.RequirementAndFulfillmentTracker.entity.Submission;
import org.accolite.RequirementAndFulfillmentTracker.model.SubmissionDTO;
import org.accolite.RequirementAndFulfillmentTracker.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/submission")
@CrossOrigin(origins = "http://localhost:4200/")
public class SubmissionsController {

    @Autowired
    private SubmissionService submissionsService;

    @PostMapping("/create")
    public ResponseEntity<SubmissionDTO> createSubmission(@RequestBody SubmissionDTO submission) {

        return submissionsService.createSubmission(submission);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SubmissionDTO>> getAllSubmissions(){
        return submissionsService.getAllSubmissions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionDTO> getSubmissionById(@PathVariable Long id) {
        return submissionsService.getSubmissionById(id);
    }

    @PutMapping("/{id}/update")

    public ResponseEntity<SubmissionDTO> updateSubmission(@PathVariable Long id, @RequestBody SubmissionDTO updatedSubmission) {
        return submissionsService.updateSubmission(id, updatedSubmission);
    }
    // not working
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteSubmission(@PathVariable Long id) {
        submissionsService.deleteSubmission(id);
        return ResponseEntity.ok("Successfully deleted submission");
    }
}