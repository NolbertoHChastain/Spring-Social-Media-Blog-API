package com.example.service;

import java.util.Optional;

import javax.security.sasl.AuthenticationException;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidRegistrationException;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    /**
     * registers new {@code Account} 
     * 
     * @param newAccount the account to register
     * @return the newly registered account
     */
    public Account register(Account newAccount) {
        if (!(newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4)) {
            Optional<Account> existingAccount = Optional.ofNullable(accountRepository.findByUsername(newAccount.getUsername()));
            if (!existingAccount.isPresent()) {
                return accountRepository.save(newAccount);
            } else throw new DuplicateUsernameException("An account with that username already exists");
        } else throw new InvalidRegistrationException("Invalid account details");
    }

    /**
     * verifies {@code Account} login
     * 
     * @param account the account to verify
     * @return the verified {@code Account}
     */
    public Account login(Account account) throws AuthenticationException {
        Optional<Account> existingAccount = Optional.ofNullable(accountRepository.findByUsername(account.getUsername()));

        if (existingAccount.isPresent() && account.getPassword().equals(existingAccount.get().getPassword())) {
            return existingAccount.get();
        } else throw new AuthenticationException("Invalid credentials");
    }
}
