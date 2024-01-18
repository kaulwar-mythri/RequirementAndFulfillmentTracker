package org.accolite.RequirementAndFulfillmentTracker.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequirementDTO {
    long requirementID;
    String startDate;
    String endDate;
    long requiredNo;
    String jobDescription;
    String hiringManager;
    AccountDTO account;
}
