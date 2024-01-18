package org.accolite.RequirementAndFulfillmentTracker.controller;


<<<<<<< HEAD
import org.accolite.RequirementAndFulfillmentTracker.entity.Requirement;
=======
import jakarta.mail.MessagingException;
import org.accolite.RequirementAndFulfillmentTracker.entity.Requirement;
import org.accolite.RequirementAndFulfillmentTracker.service.EmailNotificationService;
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
import org.accolite.RequirementAndFulfillmentTracker.service.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

<<<<<<< HEAD
import java.util.Collections;
import java.util.List;
import java.util.Set;

=======
import java.util.List;
import java.util.Set;


>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
@RestController
@RequestMapping("/api/requirement")
public class RequirementController {
    @Autowired
    private RequirementService requirementService;

<<<<<<< HEAD
=======
    @Autowired
    private EmailNotificationService emailNotificationService;
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
    // Endpoint to create a new requirement
    @PostMapping("/create")
    public ResponseEntity<Requirement> createRequirement(@RequestBody Requirement requirement) {
        Requirement createdRequirement = requirementService.createRequirement(requirement);
        return new ResponseEntity<>(createdRequirement, HttpStatus.CREATED);
    }

<<<<<<< HEAD

=======
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
    // Endpoint to update an existing requirement by ID
    @PutMapping("/{id}/update")
    public ResponseEntity<Requirement> updateRequirement(@PathVariable Long id, @RequestBody Requirement requirement) {
        Requirement updatedRequirement = requirementService.updateRequirement(id, requirement);
        return new ResponseEntity<>(updatedRequirement, HttpStatus.OK);
    }

    // Endpoint to get a list of all requirements
    @GetMapping("/all")
    public ResponseEntity<List<Requirement>> getAllRequirements() {
        List<Requirement> requirements = requirementService.getAllRequirements();
        return new ResponseEntity<>(requirements, HttpStatus.OK);
    }

    // Endpoint to get a requirement by ID
    @GetMapping("/{id}")
    public ResponseEntity<Requirement> getRequirementById(@PathVariable Long id) {
        Requirement requirement = requirementService.getRequirementById(id);
        return new ResponseEntity<>(requirement, HttpStatus.OK);
    }

    // Endpoint to delete a requirement by ID
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteRequirement(@PathVariable Long id) {
        requirementService.deleteRequirement(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
<<<<<<< HEAD

    // Endpoint to alert bench for a bench manager
    @PostMapping("/alertBench")
    public ResponseEntity<Void> alertBench(@RequestParam Long benchManagerId, @RequestBody Set<Long> requirementIds) {
=======
    // kafka
    // Endpoint to alert bench for a bench manager
    @PostMapping("/alertBench")
    public ResponseEntity<Void> alertBench(@RequestParam Long benchManagerId, @RequestBody Set<Long> requirementIds) throws MessagingException {
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
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
<<<<<<< HEAD
=======


>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
