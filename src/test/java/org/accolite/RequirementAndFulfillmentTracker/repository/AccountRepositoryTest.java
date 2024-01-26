package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.HierarchyTag;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Test
    public void addAccount() {
        Account account = Account.builder()
                .name("Fidelity")
                .hierarchyTag(HierarchyTag.CLIENT)
                .parentId(1)
                .build();

        accountRepository.save(account);
    }


}
