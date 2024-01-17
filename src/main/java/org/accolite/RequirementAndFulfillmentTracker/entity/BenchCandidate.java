
package org.accolite.RequirementAndFulfillmentTracker.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity

@Table(name = "bench_candidates")
public class BenchCandidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CandidateStatus status;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bench_candidate_id") // Name of the foreign key column
    private Set<Skill> skill = new HashSet<>();

    private int benchPeriod;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "benchManagerID", referencedColumnName = "id")
    private UserRole benchManager;



    public UserRole getBenchManager() {
        return benchManager;
    }

    public void setBenchManager(UserRole benchManager) {
        this.benchManager = benchManager;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CandidateStatus getStatus() {
        return status;
    }

    public void setStatus(CandidateStatus status) {
        this.status = status;
    }

    public Set<Skill> getSkill() {
        return skill;
    }

    public void setSkill(Set<Skill>  skill) {
        this.skill = skill;
    }



    public int getBenchPeriod() {
        return benchPeriod;
    }

    public void setBenchPeriod(int benchPeriod) {
        this.benchPeriod = benchPeriod;
    }


    @Override
    public String toString() {
        return "BenchCandidate{" +
                "id=" + id +
                ", status=" + status +
                ", skill=" + skill +
                ", benchPeriod=" + benchPeriod +
                ", benchManager=" + benchManager +
                '}';
    }

    public BenchCandidate(Long id, CandidateStatus status, Set<Skill> skill, int benchPeriod, UserRole benchManager) {
        this.id = id;
        this.status = status;
        this.skill = skill;
        this.benchPeriod = benchPeriod;
        this.benchManager = benchManager;
    }

    public BenchCandidate(){

    }


//    public void addSubmission(Submission submission) {
//        submission.setCandidateId(this.id);
//        this.submissions.add(submission);
//    }
//    Avoiding Null Checks
}
