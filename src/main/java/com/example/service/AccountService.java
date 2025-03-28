package com.example.service;

import com.example.entity.*;
import com.example.repository.AccountRepository;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public boolean register(Account account) {
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            System.out.println("Invalid username or password. Please retry credentials.");
            return false;
        }

        Optional<Account> existingAcc = accountRepository.findByUsername(account.getUsername());
        if (existingAcc.isPresent()) {
            System.out.println("This username already exists. Please retry with another.");
            return false;
        }

        accountRepository.save(account);
        return true;
    }

    public Optional<Account> login(Account account) {
        Optional<Account> existingAcc = accountRepository.findByUsername(account.getUsername());
        
        if (existingAcc.isPresent() && existingAcc.get().getPassword().equals(account.getPassword())) {
            return existingAcc;
        }
        else {
            System.out.println("Username or password invalid. Please try again.");
            return Optional.empty();
        }
    }

}
