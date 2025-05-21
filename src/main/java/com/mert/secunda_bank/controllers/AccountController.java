package com.mert.secunda_bank.controllers;

import com.mert.secunda_bank.dto.AccountResponseDTO;
import com.mert.secunda_bank.mappers.AccountMapper;
import com.mert.secunda_bank.models.Account;
import com.mert.secunda_bank.models.Bill;
import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.services.AccountService;
import com.mert.secunda_bank.dto.UpdateAccountRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<AccountResponseDTO> getAccounts() {
        try {
            List<Account> accounts = accountService.getAllAccounts();
            return AccountMapper.toAccountResponseDTOList(accounts);
        } catch (Exception e) {
            throw new RuntimeException("Accounts could not be fetched", e);
        }
    }
    // single account controllers
    @GetMapping("/{id}")
    public AccountResponseDTO getAccountByAccountNumber(@PathVariable Long id) {
        try {
            Account account = accountService.getAccountByAccountNumber(id);
            return AccountMapper.toAccountResponseDTO(account);
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
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long id, @RequestBody UpdateAccountRequestDTO updateAccountRequestDTO) {
        try {
            Account updatedAccount = accountService.updateAccount(id, updateAccountRequestDTO);
            AccountResponseDTO responseDTO = AccountMapper.toAccountResponseDTO(updatedAccount);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Account could not be updated: " + e.getMessage(), e);
        }
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
