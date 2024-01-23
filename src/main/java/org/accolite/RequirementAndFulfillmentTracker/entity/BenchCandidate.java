package org.accolite.RequirementAndFulfillmentTracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BenchCandidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // added bench candidate name
    private String candidateName;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CandidateStatus status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bench_candidate_id") // Name of the foreign key column
    private Set<Skill> skills = new HashSet<>();  //remove
    private String candidate_skill_description;
    private int benchPeriod;
    //start-date and end-date

    // shouldn't this be mapped to userrole ??
    @ManyToOne
    @JoinColumn(
            name="user-role_id",
            referencedColumnName = "id"
    )
    // replaced benchManagerId with Userrole attribute
    private UserRole benchManager;
}