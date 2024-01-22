package org.accolite.RequirementAndFulfillmentTracker.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequirementDTO {
    long requirementId;
    String startDate;
    String endDate;
    long requiredNo;
    long fulfilledNo;
    String jobDescription;
    String hiringManager;
    AccountDTO account;
}
