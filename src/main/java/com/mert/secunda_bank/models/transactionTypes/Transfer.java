package com.mert.secunda_bank.models.transactionTypes;

import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.enums.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer extends Transaction {
    private String receiverAccountNumber;
    private String senderAccountNumber;

    public Transfer(BigDecimal amount, CurrencyTypes currencyTypes, String receiverAccountNumber, String senderAccountNumber) {
        super(amount,
              LocalDateTime.now(),
                currencyTypes,
              TransactionType.TRANSFER,
              "PENDING",
              "Deposit transaction",
              BigDecimal.ZERO);
        this.receiverAccountNumber = receiverAccountNumber;
        this.senderAccountNumber = senderAccountNumber;
    }

    @Override
    public void execute() {
        // transfer code
        this.setStatus("COMPLETED");
        System.out.println("Transfer completed to" + getReceiverAccountNumber() + " by " + getSenderAccountNumber());
    }
    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }
}
