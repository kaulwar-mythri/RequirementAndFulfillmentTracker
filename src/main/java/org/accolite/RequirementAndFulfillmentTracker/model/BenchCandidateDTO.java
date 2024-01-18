package org.accolite.RequirementAndFulfillmentTracker.model;

import lombok.*;
import org.accolite.RequirementAndFulfillmentTracker.entity.CandidateStatus;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BenchCandidateDTO {
    long id;
    CandidateStatus candidateStatus;
    String skill;
    int benchPeriod;
    long benchManagerID;
}
