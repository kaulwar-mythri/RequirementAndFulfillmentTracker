package org.accolite.RequirementAndFulfillmentTracker.controller;

// controller/BenchCandidateController.java


import org.accolite.RequirementAndFulfillmentTracker.entity.BenchCandidate;
import org.accolite.RequirementAndFulfillmentTracker.service.BenchCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/bench")
public class BenchCandidateController {

    @Autowired
    private BenchCandidateService benchCandidateService;

    @PostMapping("/addCandidate")
    public BenchCandidate addCandidate(@RequestBody BenchCandidate candidate) {
        return benchCandidateService.addCandidate(candidate);
    }

    @GetMapping("/all")
    public List<BenchCandidate> getAllCandidates() {
        return benchCandidateService.getAllCandidates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BenchCandidate> getCandidateById(@PathVariable Long id) {
        Optional<BenchCandidate> candidate = benchCandidateService.getCandidateById(id);
        return candidate.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/update")
    public BenchCandidate updateCandidate(@PathVariable Long id, @RequestBody BenchCandidate updatedCandidate) {
        return benchCandidateService.updateCandidate(id, updatedCandidate);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        benchCandidateService.deleteCandidate(id);
        return ResponseEntity.noContent().build();
    }
}

