package com.mert.secunda_bank.models.transactionTypes;

import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.enums.TransactionTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Deposit extends Transaction {

    private Long receiverAccountNumber;

    public Deposit() {
        // default
    }

    public Deposit(BigDecimal amount, CurrencyTypes currencyTypes, Long receiverAccountNumber) {
        super(amount,
              LocalDateTime.now(),
              currencyTypes,
              TransactionTypes.DEPOSIT,
              "PENDING",
              "Deposit transaction",
              BigDecimal.ZERO);
        this.receiverAccountNumber = receiverAccountNumber;
    }

    @Override
    public void execute() {
        // after deposit code in service layer
        this.setStatus("COMPLETED");
        System.out.println("Deposit process completed for account: " + this.getReceiverAccountNumber() + " amount: " + getAmount());
    }

    public Long getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

}
