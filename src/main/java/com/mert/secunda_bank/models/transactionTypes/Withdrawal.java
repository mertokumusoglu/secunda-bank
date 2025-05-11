package com.mert.secunda_bank.models.transactionTypes;

import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Withdrawal extends Transaction {

    private Long senderAccountNumber;

    public Withdrawal() {

    }

    public Withdrawal(BigDecimal amount, CurrencyTypes currencyTypes, Long senderAccountNumber) {
        super(amount,
              LocalDateTime.now(),
              currencyTypes,
              TransactionType.WITHDRAWAL,
              "PENDING",
              "Withdrawal transaction",
              BigDecimal.ZERO);
        this.senderAccountNumber = senderAccountNumber;
    }

    public Long getSenderAccountNumber() {
        return senderAccountNumber;
    }

    @Override
    public void execute() {
        // after withdrawal code in service layer
        this.setStatus("COMPLETED");
        System.out.println("Withdrawal process completed for account: " + this.getSenderAccountNumber() + " amount: " + this.getAmount());
    }
}
