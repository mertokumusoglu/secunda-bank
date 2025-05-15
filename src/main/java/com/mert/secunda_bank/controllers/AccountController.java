package com.mert.secunda_bank.controllers;

import com.mert.secunda_bank.models.Account;
import com.mert.secunda_bank.models.Bill;
import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.services.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;
    AccountController(final AccountService accountService) {
        this.accountService = accountService;
    }
    @GetMapping("")
    public List<Account> getAccounts() {
        try {
            return accountService.getAllAccounts();        
        } catch (Exception e) {
            throw new RuntimeException("Accounts could not be fetched", e);
        }
    }
    // single account controllers
    @GetMapping("/{id}")
    public Account getAccountByAccountNumber(@PathVariable Long id) {
        try {
            return accountService.getAccountByAccountNumber(id);
        } catch (Exception e) {
            throw new RuntimeException("Account with Account Number " + id + " could not be fetched", e);
        }
    }
    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactionsByAccountNumber(@PathVariable Long id) {
        try {
            return accountService.getTransactions(id);
        } catch (Exception e) {
            throw new RuntimeException("Transactions could not be fetched", e);
        }
    }
    @GetMapping("/{id}/bills")
    public List<Bill> getBillsByAccountNumber(@PathVariable Long id) {
        try {
            return accountService.getBills(id);
        } catch (Exception e) {
            throw new RuntimeException("Bills could not be fetched", e);
        }
    }
    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody Account updatedAccount) {
        try {
            return accountService.updateAccount(id, updatedAccount);
        } catch (Exception e) {
            throw new RuntimeException("Account could not be updated", e);
        }
    }
    @PutMapping("/{id}/reset-password") // identity number and oldPassword will comes from session
    public Account resetPassword(@PathVariable Long identityNumber, String oldPassword,@RequestBody Account account) {
        try {
            accountService.resetPassword(identityNumber, oldPassword, account.getPassword());
        } catch (Exception e) {
            throw new RuntimeException("Password could not be reset", e);
        }
        return account;
    }
    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        try {
            accountService.deleteAccount(id);
            // routing to homepage will be added
        } catch (Exception e) {
            throw new RuntimeException("Account with id " + id + " could not be deleted", e);
        }
    }

}
