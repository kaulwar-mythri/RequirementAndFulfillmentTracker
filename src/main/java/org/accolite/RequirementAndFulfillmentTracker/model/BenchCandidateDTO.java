package org.accolite.RequirementAndFulfillmentTracker.model;

import lombok.*;
import org.accolite.RequirementAndFulfillmentTracker.entity.CandidateStatus;
import org.accolite.RequirementAndFulfillmentTracker.entity.Skill;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BenchCandidateDTO {
    long id;
    CandidateStatus candidateStatus;
    String candidateName;
    Set<Skill> skills;
    int benchPeriod;
    UserRoleDTO benchManager;
}
