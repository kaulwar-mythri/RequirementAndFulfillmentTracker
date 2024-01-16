package org.accolite.RequirementAndFulfillmentTracker.controller;
import org.accolite.RequirementAndFulfillmentTracker.entity.Submission;
import org.accolite.RequirementAndFulfillmentTracker.service.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/submission")
@CrossOrigin(origins = "http://localhost:4200/")
public class SubmissionsController {

    @Autowired
    private SubmissionService submissionsService;

    @PostMapping("/create")
    public  Submission createSubmission(@RequestBody Submission submission) {

        return submissionsService.createSubmission(submission);
    }

    @GetMapping("/all")
    public List<Submission> getAllSubmissions(){
        return submissionsService.getAllSubmissions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> getSubmissionById(@PathVariable Long id) {
        Optional<Submission> submission = submissionsService.getSubmissionById(id);
        return submission.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/update")
    public Submission updateSubmission(@PathVariable Long id, @RequestBody Submission updatedSubmission) {
        return submissionsService.updateSubmission(id, updatedSubmission);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteSubmission(@PathVariable Long id) {
        submissionsService.deleteSubmission(id);
        return ResponseEntity.noContent().build();
    }
}