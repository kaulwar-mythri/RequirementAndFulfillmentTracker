package org.accolite.RequirementAndFulfillmentTracker.service;

import org.accolite.RequirementAndFulfillmentTracker.entity.BenchCandidate;
import org.accolite.RequirementAndFulfillmentTracker.model.BenchCandidateDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface BenchCandidateService {
    ResponseEntity<BenchCandidateDTO> addCandidate(BenchCandidateDTO candidate);

    List<BenchCandidateDTO> getAllCandidates();

    ResponseEntity<BenchCandidateDTO> updateCandidate(Long id, BenchCandidateDTO updatedCandidate);

    ResponseEntity<String> deleteCandidate(Long id);
}
