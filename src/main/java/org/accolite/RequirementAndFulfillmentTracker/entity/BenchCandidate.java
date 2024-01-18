package org.accolite.RequirementAndFulfillmentTracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BenchCandidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // added bench candidate name
    private String candidateName;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CandidateStatus status;
    private String skill;
    private int benchPeriod;

    // shouldn't this be mapped to userrole ??
    @ManyToOne
    @JoinColumn(
            name="user-role_id",
            referencedColumnName = "id"
    )
    // replaced benchManagerId with Userrole attribute
    private UserRole benchManager;
}