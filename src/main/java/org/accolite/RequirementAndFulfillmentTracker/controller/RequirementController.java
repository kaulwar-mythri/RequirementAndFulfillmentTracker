package org.accolite.RequirementAndFulfillmentTracker.controller;


import jakarta.mail.MessagingException;
import org.accolite.RequirementAndFulfillmentTracker.entity.Requirement;
import org.accolite.RequirementAndFulfillmentTracker.service.EmailNotificationService;
import org.accolite.RequirementAndFulfillmentTracker.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/requirement")
public class RequirementController {
    @Autowired
    private RequirementService requirementService;

    @Autowired
    private EmailNotificationService emailNotificationService;
    // Endpoint to create a new requirement

//    @PostMapping("/createWithNewAccount")
//    public ResponseEntity<Requirement> createRequirementWithNewAccount(@RequestBody Requirement requirement){
//        System.out.println("requirement received" + requirement);
//        Requirement newRequirement = requirementService.createRequirementWithNewAccount(requirement);
//        return new ResponseEntity<>(newRequirement, HttpStatus.CREATED);
//    }

    // working
    @PostMapping("/create")
    public ResponseEntity<Requirement> createRequirement(@RequestBody Requirement requirement) {
        System.out.println("requirement received" + requirement);
        Requirement createdRequirement = requirementService.createRequirement(requirement);

        return new ResponseEntity<>(createdRequirement, HttpStatus.CREATED);
    }

    // Endpoint to update an existing requirement by ID

    // working
    @PutMapping("/{id}/update")
    public ResponseEntity<Requirement> updateRequirement(@PathVariable Long id, @RequestBody Requirement requirement) {
        Requirement updatedRequirement = requirementService.updateRequirement(id, requirement);
        return new ResponseEntity<>(updatedRequirement, HttpStatus.OK);
    }

    // Endpoint to get a list of all requirements
    // working
    @GetMapping("/all")
    public ResponseEntity<List<Requirement>> getAllRequirements() {
        List<Requirement> requirements = requirementService.getAllRequirements();
        return new ResponseEntity<>(requirements, HttpStatus.OK);
    }

    // Endpoint to get a requirement by ID
    // working
    @GetMapping("/{id}")
    public ResponseEntity<Requirement> getRequirementById(@PathVariable Long id) {
        Requirement requirement = requirementService.getRequirementById(id);
        return new ResponseEntity<>(requirement, HttpStatus.OK);
    }

    // Endpoint to delete a requirement by ID
    // working
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteRequirement(@PathVariable("id") Long id) {
        requirementService.deleteRequirement(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // kafka
    // Endpoint to alert bench for a bench manager
    @PostMapping("/alertBench")
    public ResponseEntity<Void> alertBench(@RequestParam Long benchManagerId, @RequestBody Set<Long> requirementIds) throws MessagingException {
        requirementService.alertBench(benchManagerId, requirementIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Endpoint to alert hiring for a hiring manager
    @PostMapping("/alertHiring")
    public ResponseEntity<Void> alertHiring(@RequestParam Long hiringManagerId, @RequestBody Set<Long> requirementIds) {
        requirementService.alertHiring(hiringManagerId, requirementIds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

