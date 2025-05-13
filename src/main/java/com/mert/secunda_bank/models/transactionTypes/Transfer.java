package com.mert.secunda_bank.models.transactionTypes;

import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.enums.TransactionTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer extends Transaction {
    private Long receiverAccountNumber;
    private Long senderAccountNumber;

    public Transfer() {

    }

    public Transfer(BigDecimal amount, CurrencyTypes currencyTypes, Long receiverAccountNumber, Long senderAccountNumber) {
        super(amount,
              LocalDateTime.now(),
              currencyTypes,
              TransactionTypes.TRANSFER,
              "PENDING",
              "Deposit transaction",
              BigDecimal.ZERO);
        this.receiverAccountNumber = receiverAccountNumber;
        this.senderAccountNumber = senderAccountNumber;
    }

    @Override
    public void execute() {
        // after transfer code in service layer
        this.setStatus("COMPLETED");
        System.out.println("Transfer completed to" + getReceiverAccountNumber() + " by " + getSenderAccountNumber());
    }
    public Long getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public Long getSenderAccountNumber() {
        return senderAccountNumber;
    }
}
