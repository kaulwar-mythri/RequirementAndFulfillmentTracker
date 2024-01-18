package org.accolite.RequirementAndFulfillmentTracker.entity;

<<<<<<< HEAD
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
=======
import jakarta.persistence.*;
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
*           Sample requirements json
*
*           {
              "requirementID": 1,
              "startDate": "2024-01-01",
              "endDate": "2024-12-31",
              "requiredNo": 3,
              "technologyToExperienceMap": {
                "Java": "Senior",
                "Spring Boot": "Intermediate",
                "React": "Junior"
              },
              "job_description": "Software Developer",
              "hiring_manager": "John Doe"
            }

* */

=======
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Requirement {
    @Id
<<<<<<< HEAD
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long requirementID;
=======
    @GeneratedValue(strategy= GenerationType.AUTO)
    Long requirementId;
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2

    private String startDate;
    private String endDate;
    private Long requiredNo;
<<<<<<< HEAD
//    private HashMap<Technology, Experience> technologyToExperienceMap; // this shouldn't be this ,
    private Set<Skill> skillSet;


    private String job_description;
    private String hiring_manager;

    public void addSkills(Skill skill){
        if(skillSet == null) skillSet = new HashSet<>();
        skillSet.add(skill);

    }
    // add mapping to account id
    // one account can contain multiple requirements
=======

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
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
}
