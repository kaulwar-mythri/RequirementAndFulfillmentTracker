package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.HierarchyTag;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Test
    public void addAccount() {
        Account account = Account.builder()
                .name("Morgan Stanley")
                .hierarchyTag(HierarchyTag.CLIENT)
                .parentId(1)
                .build();

        accountRepository.save(account);
    }

    @Test
    public void updateUser() {
        UserRole userRole = userRoleRepository.findByEmailId("kaulwar.mythri@accolitedigital.com").orElse(null);
        Account account = Account.builder()
                .name("Golmann Sachs")
                .hierarchyTag(HierarchyTag.CLIENT)
                .parentId(1)
                .build();

        Set<Account> accountSet = userRole.getAccounts();
        if (accountSet == null)
            accountSet = new HashSet<>();
        accountSet.add(account);

        userRole.setAccounts(accountSet);

        userRoleRepository.save(userRole);

    }
}