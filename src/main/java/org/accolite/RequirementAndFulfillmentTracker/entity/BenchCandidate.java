package org.accolite.RequirementAndFulfillmentTracker.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "bench_candidates")
public class BenchCandidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private CandidateStatus status;
    private String skill;
    private int benchPeriod;
    private Long benchManagerID;
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

    public String getSkillSet() {
        return skill;
    }

    public void setSkillSet(String  skill) {
        this.skill = skill;
    }



    public int getBenchPeriod() {
        return benchPeriod;
    }

    public void setBenchPeriod(int benchPeriod) {
        this.benchPeriod = benchPeriod;
    }

    public Long getBenchManagerID() {
        return benchManagerID;
    }

    public void setBenchManagerID(Long benchManagerID) {
        this.benchManagerID = benchManagerID;
    }

    @Override
    public String toString() {
        return "BenchCandidate{" +
                "id=" + id +
                ", status=" + status +
                ", skill='" + skill+ '\'' +

                ", benchPeriod=" + benchPeriod +
                ", benchManagerID=" + benchManagerID +
                '}';
    }

    public BenchCandidate(Long id, CandidateStatus status, String skill ,int benchPeriod, Long benchManagerID) {
        this.id = id;
        this.status = status;
        this.skill = skill;

        this.benchPeriod = benchPeriod;
        this.benchManagerID = benchManagerID;
    }
    public BenchCandidate(){

    }


//    public void addSubmission(Submission submission) {
//        submission.setCandidateId(this.id);
//        this.submissions.add(submission);
//    }
//    Avoiding Null Checks
}
