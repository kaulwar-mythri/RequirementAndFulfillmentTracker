package org.accolite.RequirementAndFulfillmentTracker.service;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.model.AccountDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AccountService {
    public ResponseEntity<AccountDTO> createAccount(AccountDTO account);

    public ResponseEntity<AccountDTO> updateAccount(long id, AccountDTO updatedAccount);

    public ResponseEntity<List<AccountDTO>> getAllAccounts();

//    public ResponseEntity<String> deleteAccountById(long id);
}
