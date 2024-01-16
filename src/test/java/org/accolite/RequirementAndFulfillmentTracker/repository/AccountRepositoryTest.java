package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.HierarchyTag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {

    @Autowired
    private  AccountRepository repository;

    @Test
    public void saveAccount(){
        Account account = Account.builder()
                .parentId(1)
                .hierarchyTag(HierarchyTag.DEPARTMENT)
                .name("MA")
                .build();
        repository.save(account);
    }

}