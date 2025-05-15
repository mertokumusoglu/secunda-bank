package com.mert.secunda_bank.services;

import com.mert.secunda_bank.models.enums.BillTypes;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.transactionTypes.Deposit;
import com.mert.secunda_bank.models.transactionTypes.Payment;
import com.mert.secunda_bank.models.transactionTypes.Transfer;
import com.mert.secunda_bank.models.transactionTypes.Withdrawal;

import jakarta.transaction.InvalidTransactionException;

import java.math.BigDecimal;

import org.hibernate.TransactionException;

public class TransactionFactory {

    public static Withdrawal createWithdrawalTransaction(Long senderAccountNumber, BigDecimal amount, CurrencyTypes currencyType) {
        try {
            validateTransactionParameters(amount, currencyType);
            return new Withdrawal(amount, currencyType, senderAccountNumber);
        } catch (Exception e) {
            throw new TransactionException("Transaction failed" + e);
        }
    }
    public static Deposit createDepositTransaction(BigDecimal amount, CurrencyTypes currencyType, Long receiverAccountNumber) {
        try {
            validateTransactionParameters(amount, currencyType);
            return new Deposit(amount, currencyType, receiverAccountNumber);
        } catch (Exception e) {
            throw new TransactionException("Transaction failed" + e);
        }
    }
    public static Transfer createTransferTransaction(BigDecimal amount, CurrencyTypes currencyType, Long receiverAccountNumber, Long senderAccountNumber) {
        try {
            validateTransactionParameters(amount, currencyType);
            return new Transfer(amount, currencyType, receiverAccountNumber, senderAccountNumber);
        } catch (Exception e) {
            throw new TransactionException("Transaction failed" + e);
        }
    }
    public static Payment createPaymentTransaction(BigDecimal amount, CurrencyTypes currencyType, BillTypes billTypes) {
        try {
            validateTransactionParameters(amount, currencyType);
            return new Payment(amount, currencyType, billTypes);
        } catch (Exception e) {
            throw new TransactionException("Transaction failed" + e);
        }
    }

    // VALIDATE
    public static void validateTransactionParameters(BigDecimal amount, CurrencyTypes currencyType) 
    throws InvalidTransactionException {
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Amount must be positive");
        }
        if(currencyType == null) {
            throw new InvalidTransactionException("Currency type must be specified");
        }
    }
}