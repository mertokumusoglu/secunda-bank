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
        return accountService.getAllAccounts();
    }
    // single account controllers
    @GetMapping("/{id}")
    public Account getAccountByAccountNumber(@PathVariable Long id) {
        return accountService.getAccountByAccountNumber(id);
    }
    @GetMapping("/{id}/transactions")
    public List<Transaction> getTransactionsByAccountNumber(@PathVariable Long id) {
        return accountService.getTransactions(id);
    }
    @GetMapping("/{id}/bills")
    public List<Bill> getBillsByAccountNumber(@PathVariable Long id) {
        return accountService.getBills(id);
    }
    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody Account updatedAccount) {
        return accountService.updateAccount(id, updatedAccount);
    }
    @PutMapping("/{id}/reset-password") // identity number and oldPassword will comes from session
    public Account resetPassword(@PathVariable Long identityNumber, String oldPassword,@RequestBody Account account) {
        accountService.resetPassword(identityNumber, oldPassword, account.getPassword());
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
