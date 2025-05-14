package com.mert.secunda_bank.services;

import com.mert.secunda_bank.models.Account;
import com.mert.secunda_bank.models.Bill;
import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.repositories.AccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {


    private final AccountRepository accountRepository;
    AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
    public Account getAccountByAccountNumber(Long accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

    }
    public Account getAccountByIdentityNumber(Long identityNumber) {
        return accountRepository.findByIdentityNumber(identityNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
    }
    public Account createAccount(Account account) {
        return accountRepository.save(account);
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
    public List<Transaction> getTransactions(Long accountNumber) {
        Account account = getAccountByAccountNumber(accountNumber);
        return account.getTransactions();
    }
    public List<Bill> getBills(Long accountNumber) {
        Account account = getAccountByAccountNumber(accountNumber);
        return account.getBills();
    }
    public Account updateAccount(Long accountNumber, Account updatedAccount) {
        Account account = getAccountByAccountNumber(accountNumber);
        // I don't add validation things for that. I know i must
        account.setEmail(updatedAccount.getEmail());
        account.setName(updatedAccount.getName());
        account.setPhoneNumber(updatedAccount.getPhoneNumber());
        return account;
    }
    public void deleteAccount(Long AccountNumber) {
        Account account = getAccountByAccountNumber(AccountNumber);
        accountRepository.delete(account);
    }
}
