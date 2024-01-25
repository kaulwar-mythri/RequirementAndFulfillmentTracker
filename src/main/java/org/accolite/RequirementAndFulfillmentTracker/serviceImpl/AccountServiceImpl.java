package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import org.accolite.RequirementAndFulfillmentTracker.config.JWTService;
import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.Role;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.exception.UserUnauthorisedException;
import org.accolite.RequirementAndFulfillmentTracker.model.AccountDTO;
import org.accolite.RequirementAndFulfillmentTracker.repository.AccountRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.AccountService;
import org.accolite.RequirementAndFulfillmentTracker.utils.EntityToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    EntityToDTO entityToDTO;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    JWTService jwtService;

    List<Role> authorised_roles = new ArrayList<>(List.of(Role.SUPER_ADMIN, Role.ADMIN));
    @Override
    public ResponseEntity<AccountDTO> createAccount(AccountDTO account) {
        checkIfAuthorized();
        Account newAccount = Account.builder()
                .name(account.getName())
                .parentId(account.getParentId())
                .hierarchyTag(account.getHierarchyTag())
                .build();
        newAccount = accountRepository.save(newAccount);
        return ResponseEntity.ok(entityToDTO.getAccountDTO(newAccount));
    }
    @Override
    public ResponseEntity<AccountDTO> updateAccount(long id, AccountDTO updatedAccount) {
        checkIfAuthorized();
        Account existingAccount = accountRepository.findById(updatedAccount.getAccount_id())
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        existingAccount.setName(updatedAccount.getName());
        existingAccount.setParentId(updatedAccount.getParentId());
        existingAccount.setHierarchyTag(updatedAccount.getHierarchyTag());
        existingAccount = accountRepository.save(existingAccount);

        return ResponseEntity.ok(entityToDTO.getAccountDTO(existingAccount));
    }

    @Override
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        checkIfAuthorized();
        return ResponseEntity.ok(accountRepository.findAll().stream().map(account -> {
            return entityToDTO.getAccountDTO(account);
        }).toList());
    }

//    @Override
//    public ResponseEntity<String> deleteAccountById(long id) {
//        checkIfAuthorized();
//        accountRepository.deleteById(id);
//        return ResponseEntity.ok("Account deleted");
//    }

    private void checkIfAuthorized() {
        UserRole user = userRoleRepository.findByEmailId(jwtService.getUser())
                        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!authorised_roles.contains(user.getRole()))
            throw new UserUnauthorisedException("User is not authorised to perform the above operation");
    }
}
