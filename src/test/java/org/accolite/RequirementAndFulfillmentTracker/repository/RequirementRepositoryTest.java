package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequirementRepositoryTest {

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private AccountRepository accountRepository;
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
//                .skillSet((Set<Skill>) skill)
                .build();

    }


    @Test
    public void saveRequirementWithAccount(){
        Account account = accountRepository.findById(1L).orElse(null);
        Requirement requirement = Requirement.builder()
                .requiredNo(30L)
                .job_description("spring boot developer")
                .hiring_manager("simran")
                .endDate("March 2021")
                .startDate("July 2021")
                .account(account)
                .build();
        requirementRepository.save(requirement);
    }

}