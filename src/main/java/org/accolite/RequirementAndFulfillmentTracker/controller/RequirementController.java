package org.accolite.RequirementAndFulfillmentTracker.controller;


import jakarta.mail.MessagingException;
import org.accolite.RequirementAndFulfillmentTracker.entity.Requirement;
import org.accolite.RequirementAndFulfillmentTracker.model.RequirementDTO;
import org.accolite.RequirementAndFulfillmentTracker.service.EmailNotificationService;

import org.accolite.RequirementAndFulfillmentTracker.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/requirement")
public class RequirementController {
    @Autowired
    private RequirementService requirementService;
    @Autowired
    private EmailNotificationService emailNotificationService;

    @PostMapping("/create")
    public ResponseEntity<RequirementDTO> createRequirement(@RequestBody RequirementDTO requirement) throws MessagingException {
        return requirementService.createRequirement(requirement);
    }


    // Endpoint to update an existing requirement by ID

    // working
    @PutMapping("/{id}/update")
    public ResponseEntity<RequirementDTO> updateRequirement(@PathVariable Long id, @RequestBody RequirementDTO requirement) {
        return requirementService.updateRequirement(id, requirement);
    }

    // Endpoint to get a list of all requirements
    // working
    @GetMapping("/all")
    public ResponseEntity<List<RequirementDTO>> getAllRequirements() {
        return requirementService.getAllRequirements();
//        return new ResponseEntity<>(requirements, HttpStatus.OK);
    }



    // Endpoint to get a requirement by ID
    // Endpoint to delete a requirement by ID
    // working
//    @DeleteMapping("/{id}/delete")
//    public ResponseEntity<String> deleteRequirement(@PathVariable("id") Long id) {
//        requirementService.deleteRequirement(id);
//
//        return ResponseEntity.ok("Deleted requirement successfully");
//    }

    // Endpoint to alert bench for a bench manager
    @PostMapping("/alertBench")
    public ResponseEntity<Void> alertBench(@RequestParam Long benchManagerId, @RequestBody Set<Long> requirementIds) throws MessagingException {
        requirementService.alertBench(benchManagerId, requirementIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoint to alert hiring for a hiring manager
    @PostMapping("/alertHiring")
    public ResponseEntity<Void> alertHiring(@RequestParam Long hiringManagerId, @RequestBody Set<Long> requirementIds) throws MessagingException {
        requirementService.alertHiring(hiringManagerId, requirementIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
