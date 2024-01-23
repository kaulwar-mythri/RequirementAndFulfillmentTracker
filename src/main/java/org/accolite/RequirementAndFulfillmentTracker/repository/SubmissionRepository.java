package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.BenchCandidate;
import org.accolite.RequirementAndFulfillmentTracker.entity.Submission;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    public List<Submission> findByBenchCandidate(BenchCandidate benchCandidate);
}
