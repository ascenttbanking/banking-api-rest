package com.ascentt.bankingservice.service;

import com.ascentt.bankingservice.model.Account;
import com.ascentt.bankingservice.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public boolean createAccount(Account account) {
        if (account.getEmail() == null || account.getPassword() == null) {
            return false;
        }
        accountRepository.save(account);
        return true;
    }

    public boolean editAccount(Account account) {
        if (account.getEmail() == null || account.getPassword() == null) {
            return false;
        }
        if (accountRepository.existsById(account.getId())) {
            accountRepository.save(account);
            return true;
        }
        return false;
    }
}
