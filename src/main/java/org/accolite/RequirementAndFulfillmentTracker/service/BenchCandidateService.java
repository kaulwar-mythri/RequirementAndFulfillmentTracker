package org.accolite.RequirementAndFulfillmentTracker.service;

import org.accolite.RequirementAndFulfillmentTracker.entity.BenchCandidate;

import java.util.List;
import java.util.Optional;

public interface BenchCandidateService {
    BenchCandidate addCandidate(BenchCandidate candidate);

    List<BenchCandidate> getAllCandidates();

    Optional<BenchCandidate> getCandidateById(Long id);

    BenchCandidate updateCandidate(Long id, BenchCandidate updatedCandidate);

    void deleteCandidate(Long id);
}
