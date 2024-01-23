package org.accolite.RequirementAndFulfillmentTracker.repository;


// repository/BenchCandidateRepository.java


import org.accolite.RequirementAndFulfillmentTracker.entity.BenchCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BenchCandidateRepository extends JpaRepository<BenchCandidate, Long> {
    // You can add custom query methods if needed
}