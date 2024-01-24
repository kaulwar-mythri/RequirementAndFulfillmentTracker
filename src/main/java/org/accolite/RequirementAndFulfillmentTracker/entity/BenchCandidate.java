package org.accolite.RequirementAndFulfillmentTracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;


@Entity
@Builder
@Data
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

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "bench_candidate_id") // Name of the foreign key column
//    private Set<Skill> skill = new HashSet<>();
    private String skill;

    private int benchPeriod;


    @ManyToOne
    @JoinColumn(
            name="userRole_id",
            referencedColumnName = "id"
    )

    private UserRole benchManager;
}