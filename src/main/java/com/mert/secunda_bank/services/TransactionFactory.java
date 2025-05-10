package com.mert.secunda_bank.services;

import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.transactionTypes.Deposit;
import com.mert.secunda_bank.models.transactionTypes.Withdrawal;

import java.math.BigDecimal;

public class TransactionFactory {

    public static Withdrawal createWithdrawalTransaction(String senderAccountNumber, BigDecimal amount, CurrencyTypes currencyTypes) {
        return new Withdrawal(amount, currencyTypes, senderAccountNumber);
    }
    public static Deposit createDepositTransaction(BigDecimal amount, CurrencyTypes currencyTypes, String receiverAccountNumber) {
        return new Deposit(amount, currencyTypes, receiverAccountNumber);
    }
}