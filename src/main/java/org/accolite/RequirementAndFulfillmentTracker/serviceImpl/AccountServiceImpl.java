package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.repository.AccountRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Override
    public ResponseEntity<String> createAccount(Account account) {
        accountRepository.save(account);
        return ResponseEntity.ok("Account created");
    }

    @Autowired
    UserRoleRepository userRoleRepository;
    @Override
    public Account updateAccount(long id, Account updatedAccount) {
        Optional<Account> existingAccount = accountRepository.findById(id);

        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();
            account.setName(updatedAccount.getName());
            account.setParentId(updatedAccount.getParentId());
            account.setUserRoles(updatedAccount.getUserRoles());
            account.setHierarchyTag(updatedAccount.getHierarchyTag());
            return accountRepository.save(account);
        } else {
            return null;
            //return ResponseEntity.badRequest().body("Account not found");
        }
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public ResponseEntity<String> deleteAccountById(long id) {
        accountRepository.deleteById(id);
        return ResponseEntity.ok("Account deleted");
    }
}
