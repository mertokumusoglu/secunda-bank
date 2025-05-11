package com.mert.secunda_bank.models.transactionTypes;

import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.models.enums.BillTypes;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.enums.TransactionType;

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
              TransactionType.PAYMENT,
              "PENDING",
              "Payment transaction",
              BigDecimal.ZERO);
        this.paymentDetail = billTypes;
    }

    public String getPaymentDetail() {
        return paymentDetail.toString();
    }

    @Override
    public void execute() {}
}
