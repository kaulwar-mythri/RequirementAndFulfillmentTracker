package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.HierarchyTag;
import org.accolite.RequirementAndFulfillmentTracker.entity.Role;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRoleRepositoryTest {
    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void updateUserRole() {

        UserRole userRole = UserRole.builder()
                .id(1)
                .emailId("kaulwar.mythri@accolitedigital.com")
                .employeeId(7495)
                .role(Role.BENCH_MANAGER)
                .build();

        userRoleRepository.save(userRole);
    }
}