package org.accolite.RequirementAndFulfillmentTracker.model;

import lombok.*;
import org.accolite.RequirementAndFulfillmentTracker.entity.FulfillmentStatus;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FulfillmentDTO {
    long fulfillmentId;
    String fulfillmentDate;
    FulfillmentStatus fulfillmentStatus;
    SubmissionDTO submission;
}
