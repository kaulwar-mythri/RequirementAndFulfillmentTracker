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
//      need to ask whether to remove this skill table altogether or not??
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "bench_candidate_id") // Name of the foreign key column
//    private Set<Skill> skills = new HashSet<>();
    String benchCandidateSkills;

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