package com.mert.secunda_bank.services;

import com.mert.secunda_bank.models.Account;
import com.mert.secunda_bank.models.enums.BillTypes;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.repositories.AccountRepository;
import com.mert.secunda_bank.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    public void withdrawal(Long senderAccountNumber, BigDecimal amount) {
        if(hasValidateSufficientFunds(senderAccountNumber, amount)) {
            // withdraw transaction
        }
    }
    public void transfer(BigDecimal amount, CurrencyTypes currencyTypes, String receiverAccountNumber, String senderAccountNumber) {
        // transfer transaction
    }
    public void deposit(BigDecimal amount, CurrencyTypes currencyTypes, String receiverAccountNumber) {
        // deposit transaction
    }
    public void payment(BigDecimal amount, CurrencyTypes currencyTypes, BillTypes billTypes) {
        // payment transaction
    }


    private boolean hasValidateSufficientFunds(Long senderAccountNumber, BigDecimal amount) {
        Account account = accountRepository.findById(senderAccountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        return account.getBalance().compareTo(amount) >= 0;
    }
    private void debitAccount() {
        // take money from account
    }
    private void creditAccount() {
        // add money to account
    }
    private void saveTransaction(Object transaction) {
        // transaction saving
    }
}
