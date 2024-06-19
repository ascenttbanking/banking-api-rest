package com.ascentt.bankingservice;

import com.ascentt.bankingservice.model.Account;
import com.ascentt.bankingservice.repository.AccountRepository;
import com.ascentt.bankingservice.service.AccountService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    public AccountServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {
        Account account = new Account();
        account.setEmail("new@example.com");
        account.setPassword("password");

        when(accountRepository.save(account)).thenReturn(account);

        boolean isCreated = accountService.createAccount(account);
        assertTrue(isCreated);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testEditAccount() {
        Account account = new Account();
        account.setId(1L);
        account.setEmail("update@example.com");
        account.setPassword("newpassword");

        when(accountRepository.existsById(account.getId())).thenReturn(true);
        when(accountRepository.save(account)).thenReturn(account);

        boolean isEdited = accountService.editAccount(account);
        assertTrue(isEdited);
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testCreateAccountFail() {
        Account account = new Account();

        boolean isCreated = accountService.createAccount(account);
        assertFalse(isCreated);
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void testEditAccountFail() {
        Account account = new Account();
        account.setId(1L);

        when(accountRepository.existsById(account.getId())).thenReturn(false);

        boolean isEdited = accountService.editAccount(account);
        assertFalse(isEdited);
        verify(accountRepository, never()).save(any(Account.class));
    }
}
