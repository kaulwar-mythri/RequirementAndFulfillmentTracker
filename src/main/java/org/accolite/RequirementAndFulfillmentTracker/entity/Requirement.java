package org.accolite.RequirementAndFulfillmentTracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "skillId")
    private Set<Skill> skills = new HashSet<>();

    public Long getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getRequiredNo() {
        return requiredNo;
    }

    public void setRequiredNo(Long requiredNo) {
        this.requiredNo = requiredNo;
    }

    public String getJob_description() {
        return job_description;
    }

    public void setJob_description(String job_description) {
        this.job_description = job_description;
    }

    public String getHiring_manager() {
        return hiring_manager;
    }

    public void setHiring_manager(String hiring_manager) {
        this.hiring_manager = hiring_manager;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}