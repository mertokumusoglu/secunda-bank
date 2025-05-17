package com.mert.secunda_bank.services;

import com.mert.secunda_bank.models.Account;
import com.mert.secunda_bank.models.Bill;
import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.repositories.AccountRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    @PersistenceContext
    private EntityManager entityManager;

    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Account getAccountByAccountNumber(Long accountNumber) {
        Account account = entityManager.find(Account.class, accountNumber, LockModeType.PESSIMISTIC_WRITE);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        return account;
    }

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public Account getAccountByIdentityNumber(Long identityNumber) {
        Account account = entityManager.find(Account.class, identityNumber, LockModeType.PESSIMISTIC_WRITE);
        if (account == null) {
            throw new RuntimeException("Account not found");
        }
        return account;
    }
    @Transactional
    public Account createAccount(Long identityNumber, String name, String password, String email, String phoneNumber) {
        Account account = Account.builder()
                .identityNumber(identityNumber)
                .name(name)
                .password(password)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
        return accountRepository.save(account);
    }

    public boolean authenticate(Long identityNumber, String password) {
        // not implemented
        return true;
    }

    @Transactional
    public void resetPassword(Long identityNumber, String currentPassword, String newPassword) {
        Account account = accountRepository.findByIdentityNumber(identityNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        // hashing is not implemented
        if (!account.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Incorrect current password");
        }
        account.setPassword(newPassword);
        accountRepository.save(account);
    }

    public List<Transaction> getTransactions(Long accountNumber) {
        Account account = getAccountByAccountNumber(accountNumber);
        return account.getTransactions();
    }

    public List<Bill> getBills(Long accountNumber) {
        Account account = getAccountByAccountNumber(accountNumber);
        return account.getBills();
    }
    @Transactional
    public Account updateAccount(Long accountNumber, Account updatedAccount) {
        Account existingAccount = getAccountByAccountNumber(accountNumber);
        
        existingAccount.setName(updatedAccount.getName());
        existingAccount.setEmail(updatedAccount.getEmail());
        existingAccount.setPhoneNumber(updatedAccount.getPhoneNumber());
        
        return existingAccount;
    }
    @Transactional
    public void deleteAccount(Long AccountNumber) {
        Account account = getAccountByAccountNumber(AccountNumber);
        accountRepository.delete(account);
    }
}
