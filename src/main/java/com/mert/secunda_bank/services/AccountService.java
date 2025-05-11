package com.mert.secunda_bank.services;

import com.mert.secunda_bank.models.Account;
import com.mert.secunda_bank.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
    public Account getAccountById(Long accountNumber) {
        return accountRepository.findById(accountNumber).orElse(null);
    }
    public Account getAccountByIdentityNumber(Long identityNumber) {
        return accountRepository.findByIdentityNumber(identityNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
    public boolean authenticate(Long identityNumber, String password) {
        // not implemented
        return true;
    }
    @Transactional
    public void resetPassword(Long identityNumber,String currentPassword,String newPassword) {
        Account account = accountRepository.findByIdentityNumber(identityNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        // hashing is not implemented
        if(!account.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Incorrect current password");
        }
        account.setPassword(newPassword);
        accountRepository.save(account);
    }

    // Account delete method
}
