package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
