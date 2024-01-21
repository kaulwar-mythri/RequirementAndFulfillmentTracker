package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.Experience;
import org.accolite.RequirementAndFulfillmentTracker.entity.Requirement;
import org.accolite.RequirementAndFulfillmentTracker.entity.Skill;
import org.accolite.RequirementAndFulfillmentTracker.entity.Technology;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequirementRepositoryTest {

    @Autowired
    private RequirementRepository requirementRepository;

    @Test
    public void saveRequirement(){
        Skill skill = Skill.builder()
                .experience(Experience.JUNIOR)
                .technology(Technology.SPRING_BOOT)
                .build();
        Requirement requirement = Requirement.builder()
                .startDate("Jan 2024")
                .endDate("March 2024")
                .requiredNo(10L)
                .job_description("required advanced java skills")
                .hiring_manager("vipin")
                .skillSet((Set<Skill>) skill)
                .build();

    }

}