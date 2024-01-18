package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.HierarchyTag;
import org.accolite.RequirementAndFulfillmentTracker.entity.Requirement;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class RequirementRepositoryTest {

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private  AccountRepository accountRepository;

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