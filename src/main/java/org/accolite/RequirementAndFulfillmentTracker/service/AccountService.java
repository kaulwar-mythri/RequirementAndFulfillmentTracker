package org.accolite.RequirementAndFulfillmentTracker.service;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    public ResponseEntity<String> createAccount(Account account);

    public Account updateAccount(long id, Account updatedAccount);

    public List<Account> getAllAccounts();

    public ResponseEntity<String> deleteAccountById(long id);
}
