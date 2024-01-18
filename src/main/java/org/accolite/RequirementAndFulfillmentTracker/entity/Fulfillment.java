package org.accolite.RequirementAndFulfillmentTracker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "fulfillment")
public class Fulfillment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fulfillmentId;

    private String fulfillmentDate;
    private String fulfillmentStatus;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "submission_id")
    private Submission submission;

    // Constructors, getters, and setters

    public Fulfillment(Long fulfillmentId, String fulfillmentDate, String fulfillmentStatus, Submission submission) {
        this.fulfillmentId = fulfillmentId;
        this.fulfillmentDate = fulfillmentDate;
        this.fulfillmentStatus = fulfillmentStatus;
        this.submission = submission;
    }

    public Fulfillment() {
    }

    public Long getFulfillmentId() {
        return fulfillmentId;
    }

    public String getFulfillmentDate() {
        return fulfillmentDate;
    }

    public String getFulfillmentStatus() {
        return fulfillmentStatus;
    }

    public Submission getSubmission() {
        return submission;
    }

    public void setFulfillmentId(Long fulfillmentId) {
        this.fulfillmentId = fulfillmentId;
    }

    public void setFulfillmentDate(String fulfillmentDate) {
        this.fulfillmentDate = fulfillmentDate;
    }

    public void setFulfillmentStatus(String fulfillmentStatus) {
        this.fulfillmentStatus = fulfillmentStatus;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }
}
