package com.mert.secunda_bank.models.transactionTypes;

import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Withdrawal extends Transaction {

    private String senderAccountNumber;

    public Withdrawal(BigDecimal amount, CurrencyTypes currencyTypes, String senderAccountNumber) {
        super(amount,
              LocalDateTime.now(),
                currencyTypes,
              TransactionType.WITHDRAWAL,
              "PENDING",
              "Withdrawal transaction",
              BigDecimal.ZERO);
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    @Override
    public void execute() {
        // withdrawal code
        this.setStatus("COMPLETED");
        System.out.println("Withdrawal process completed for account: " + this.getSenderAccountNumber() + " amount: " + this.getAmount());
    }
}
