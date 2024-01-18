package org.accolite.RequirementAndFulfillmentTracker.model;

import lombok.*;
import org.accolite.RequirementAndFulfillmentTracker.entity.BenchCandidate;
import org.accolite.RequirementAndFulfillmentTracker.entity.SubmissionStatus;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SubmissionDTO {
    long submissionId;
    String submissionDate;
    String feedback;
    private SubmissionStatus submissionStatus;
    RequirementDTO requirement;
    BenchCandidateDTO benchCandidate;
}
