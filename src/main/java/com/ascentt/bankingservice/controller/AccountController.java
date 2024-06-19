package com.ascentt.bankingservice.controller;

import com.ascentt.bankingservice.model.Account;
import com.ascentt.bankingservice.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<String> createAccount(@RequestBody Account account) {
        boolean isCreated = accountService.createAccount(account);
        if (isCreated) {
            return ResponseEntity.ok("Account created successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to create account");
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editAccount(@RequestBody Account account) {
        boolean isEdited = accountService.editAccount(account);
        if (isEdited) {
            return ResponseEntity.ok("Account updated successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to update account");
        }
    }
}
