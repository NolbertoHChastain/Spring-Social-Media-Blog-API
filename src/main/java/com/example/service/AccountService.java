package com.example.service;

import java.util.Optional;

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

    public Account register(Account newAccount) {
        if (!(newAccount.getUsername().isBlank() || newAccount.getPassword().length() < 4)) {
            Optional<Account> existingAccount = Optional.ofNullable(accountRepository.findByUsername(newAccount.getUsername()));
            if (!existingAccount.isPresent()) {
                return accountRepository.save(newAccount);
            } else throw new DuplicateUsernameException("An account with that username already exists");
        } else throw new InvalidRegistrationException("Invalid account details");
    }
}
