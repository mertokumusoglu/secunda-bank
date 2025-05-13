package com.mert.secunda_bank.services;

import com.mert.secunda_bank.models.enums.BillTypes;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.transactionTypes.Deposit;
import com.mert.secunda_bank.models.transactionTypes.Payment;
import com.mert.secunda_bank.models.transactionTypes.Transfer;
import com.mert.secunda_bank.models.transactionTypes.Withdrawal;

import java.math.BigDecimal;

public class TransactionFactory {

    public static Withdrawal createWithdrawalTransaction(Long senderAccountNumber, BigDecimal amount, CurrencyTypes currencyTypes) {
        return new Withdrawal(amount, currencyTypes, senderAccountNumber);
    }
    public static Deposit createDepositTransaction(BigDecimal amount, CurrencyTypes currencyTypes, Long receiverAccountNumber) {
        return new Deposit(amount, currencyTypes, receiverAccountNumber);
    }
    public static Transfer createTransferTransaction(BigDecimal amount, CurrencyTypes currencyTypes, Long receiverAccountNumber, Long senderAccountNumber) {
        return new Transfer(amount, currencyTypes, receiverAccountNumber, senderAccountNumber);
    }
    public static Payment createPaymentTransaction(BigDecimal amount, CurrencyTypes currencyTypes, BillTypes billTypes) {
        return  new Payment(amount,currencyTypes,billTypes);
    }
}