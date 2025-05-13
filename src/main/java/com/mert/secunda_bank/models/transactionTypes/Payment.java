package com.mert.secunda_bank.models.transactionTypes;

import com.mert.secunda_bank.models.Account;
import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.models.enums.BillTypes;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.enums.TransactionTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment extends Transaction {

    private BillTypes paymentDetail;


    public Payment() {
        // default
    }

    public Payment(BigDecimal amount, CurrencyTypes currencyTypes, BillTypes billTypes) {
        super(amount,
              LocalDateTime.now(),
              currencyTypes,
              TransactionTypes.PAYMENT,
              "PENDING",
              "Payment transaction",
              BigDecimal.ZERO);
        this.paymentDetail = billTypes;
    }

    public String getPaymentDetail() {
        return paymentDetail.toString();
    }

    @Override
    public void execute() {
        // after payment code in service layer
        this.setStatus("COMPLETED");
        System.out.println("Payment process completed for account:");
    }
}
