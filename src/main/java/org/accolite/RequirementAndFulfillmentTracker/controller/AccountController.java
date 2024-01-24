package org.accolite.RequirementAndFulfillmentTracker.controller;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.model.AccountDTO;
import org.accolite.RequirementAndFulfillmentTracker.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/accounts")
public class AccountController {
    // remove all the delete functions
    // swagger me implement krna hai
    // swagger apis
    // check if can use firebase as backend
    @Autowired
    AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO account) {
        return accountService.createAccount(account);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable long id, @RequestBody AccountDTO updatedAccount) {
        return accountService.updateAccount(id, updatedAccount);
    }

    // Get All Accounts
    @GetMapping("/all")
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    // Delete Account by ID
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteAccountById(@PathVariable long id) {
        return accountService.deleteAccountById(id);
    }
}
