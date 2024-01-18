package org.accolite.RequirementAndFulfillmentTracker.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Requirement {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    Long requirementID;

    private String startDate;
    private String endDate;
    private Long requiredNo;
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
}
