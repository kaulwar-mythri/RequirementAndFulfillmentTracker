package org.accolite.RequirementAndFulfillmentTracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Requirement {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long requirementId;

    private String startDate;
    private String endDate;
    private Long requiredNo;

    private String job_description;
    private String hiring_manager;
    @ManyToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "account_id",
            referencedColumnName = "account_id"
    )
    private Account account;
}
