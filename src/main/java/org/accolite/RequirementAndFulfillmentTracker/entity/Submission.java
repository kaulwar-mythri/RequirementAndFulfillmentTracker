package org.accolite.RequirementAndFulfillmentTracker.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import jakarta.persistence.*;


@Entity
public class Submission {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long submissionId;
private String submissionDate;
private String feedback;

@Enumerated(EnumType.STRING)
private SubmissionStatus submissionStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "requirement_id") // This is the foreign key column in the Submission table
    private Requirement requirement;


    @ManyToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "benchCandidateId")
    private BenchCandidate benchCandidate;


    public Submission() {
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(SubmissionStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public BenchCandidate getBenchCandidate() {
        return benchCandidate;
    }

    public void setBenchCandidate(BenchCandidate benchCandidate) {
        this.benchCandidate = benchCandidate;
    }
}


