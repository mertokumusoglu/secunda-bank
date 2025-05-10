package com.mert.secunda_bank.models.transactionTypes;

import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Deposit extends Transaction {

    private String receiverAccountNumber;

    public Deposit(BigDecimal amount, CurrencyTypes currencyTypes, String receiverAccountNumber) {
        super(amount,
              LocalDateTime.now(),
                currencyTypes,
              TransactionType.DEPOSIT,
              "PENDING",
              "Deposit transaction",
              BigDecimal.ZERO);
        this.receiverAccountNumber = receiverAccountNumber;
    }

    @Override
    public void execute() {
        // deposit code
        this.setStatus("COMPLETED");
        System.out.println("Withdrawal process completed for account: " + this.getReceiverAccountNumber() + " amount: " + getAmount());
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

}
