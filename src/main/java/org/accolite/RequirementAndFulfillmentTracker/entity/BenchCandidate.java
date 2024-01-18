package org.accolite.RequirementAndFulfillmentTracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "bench_candidates")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BenchCandidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CandidateStatus status;
    private String skill;
    private int benchPeriod;

    // shouldn't this be mapped to userrole ??
    @OneToOne(mappedBy = "userrole")
    // replaced benchManagerId with Userrole attribute
    private UserRole benchManager;


//    public void addSubmission(Submission submission) {
//        submission.setCandidateId(this.id);
//        this.submissions.add(submission);
//    }
//    Avoiding Null Checks
}
