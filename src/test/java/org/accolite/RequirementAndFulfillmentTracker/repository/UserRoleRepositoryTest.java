package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.HierarchyTag;
import org.accolite.RequirementAndFulfillmentTracker.entity.Role;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.utils.EntityToDTO;
import org.hibernate.annotations.NaturalId;
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
    @Autowired
    EntityToDTO entityToDTO;

    @Test
    public void saveUserRoleWithAccount() {

        UserRole userRole = UserRole.builder()
                .emailId("raft.user.from@gmail.com")
                .employeeId(7462)
                .role(Role.BENCH_MANAGER)
                .build();

        Account account = accountRepository.findById(1L).orElseThrow(() -> new ResourceNotFoundException("Account not found"));;
//        System.out.println(accountRepository.findAll());
//        Set<Account> accountSet = new HashSet<>();
//        accountSet.add(account);
//
//        userRole.setAccounts(accountSet);


        System.out.println(userRoleRepository.save(userRole));
    }


    @Test
    public void saveUserRoles(){
        UserRole userRole = userRoleRepository.findByEmailId("raft.user.from@gmail.com").orElse(null);
        Account account = Account.builder()
                .name("Morgan Stanley")
                .hierarchyTag(HierarchyTag.CLIENT)
                .parentId(1)
                .build();

        Set<Account> accountSet = userRole.getAccounts();
        if(accountSet == null) accountSet = new HashSet<>();
        accountSet.add(account);

        userRole.setAccounts(accountSet);


        System.out.println(userRoleRepository.save(userRole));
//
//        Set<Account> accountSet = new HashSet<>();
//        accountSet.add(account);
//
//        userRole.setAccounts(accountSet);

//        userRoleRepository.save(userRole);
//        UserRole benchmanager = UserRole.builder()
//                .role(Role.ADMIN)
//                .emailId("raft.user.from@gmail.com")
//                .accounts(accountSet)
//                .build();
//        Set<UserRole> userRoles = new HashSet<>();
//        userRoles.add(userRole);
//        userRoleRepository.save(userRole);
    }

//    @Test
//    public void updateUser() {
//        UserRole userRole = userRoleRepository.findByEmailId("kaulwar.mythri@accolitedigital.com").orElse(null);
//        Account account = Account.builder()
//                .name("Morgan Stanley")
//                .hierarchyTag(HierarchyTag.CLIENT)
//                .parentId(1)
//                .build();
//
//        Set<Account> accountSet = userRole.getAccounts();
//        if (accountSet == null)
//            accountSet = new HashSet<>();
//        accountSet.add(account);
//
//        userRole.setAccounts(accountSet);
//
//        userRoleRepository.save(userRole);
//
//    }
}