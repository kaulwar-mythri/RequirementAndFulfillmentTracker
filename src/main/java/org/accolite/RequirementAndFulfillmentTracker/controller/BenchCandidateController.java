package org.accolite.RequirementAndFulfillmentTracker.controller;

// controller/BenchCandidateController.java


import org.accolite.RequirementAndFulfillmentTracker.config.JWTService;
import org.accolite.RequirementAndFulfillmentTracker.entity.BenchCandidate;
import org.accolite.RequirementAndFulfillmentTracker.model.BenchCandidateDTO;
import org.accolite.RequirementAndFulfillmentTracker.service.BenchCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/bench")
public class BenchCandidateController {

    @Autowired
    private BenchCandidateService benchCandidateService;

    // working but duplicate case has not been handled
    @PostMapping("/addCandidate")
    public ResponseEntity<BenchCandidateDTO> addCandidate(@RequestBody BenchCandidateDTO candidate) {
        return benchCandidateService.addCandidate(candidate);
    }
    // working
    @GetMapping("/all")
    public List<BenchCandidateDTO> getAllCandidates() {
        return benchCandidateService.getAllCandidates();
    }

    // working
    @PutMapping("/{id}/update")
    public ResponseEntity<BenchCandidateDTO> updateCandidate(@PathVariable Long id, @RequestBody BenchCandidateDTO updatedCandidate) {
        return benchCandidateService.updateCandidate(id, updatedCandidate);
    }

    // working

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteCandidate(@PathVariable Long id) {
        return benchCandidateService.deleteCandidate(id);
    }
}