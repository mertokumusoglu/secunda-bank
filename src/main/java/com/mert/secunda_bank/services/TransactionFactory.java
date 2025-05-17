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

    public static Withdrawal createWithdrawalTransaction(Long accountNumber, BigDecimal amount, CurrencyTypes currencyType) {
        try {
            validateTransactionParameters(amount, currencyType);
            return Withdrawal.builder()
                    .accountNumber(accountNumber)
                    .amount(amount)
                    .currencyType(currencyType)
                    .build();
        } catch (Exception e) {
            throw new TransactionException("Transaction failed: " + e.getMessage());
        }
    }

    public static Deposit createDepositTransaction(BigDecimal amount, CurrencyTypes currencyType, Long accountNumber) {
        try {
            validateTransactionParameters(amount, currencyType);
            return Deposit.builder()
                    .accountNumber(accountNumber)
                    .amount(amount)
                    .currencyType(currencyType)
                    .build();
        } catch (Exception e) {
            throw new TransactionException("Transaction failed: " + e.getMessage());
        }
    }

    public static Transfer createTransferTransaction(BigDecimal amount, CurrencyTypes currencyType, Long receiverAccountNumber, Long senderAccountNumber) {
        try {
            validateTransactionParameters(amount, currencyType);
            return Transfer.builder()
                    .amount(amount)
                    .currencyType(currencyType)
                    .sender(senderAccountNumber)
                    .receiver(receiverAccountNumber)
                    .build();
        } catch (Exception e) {
            throw new TransactionException("Transaction failed: " + e.getMessage());
        }
    }

    public static Payment createPaymentTransaction(BigDecimal amount, CurrencyTypes currencyType, BillTypes billType) {
        try {
            validateTransactionParameters(amount, currencyType);
            return Payment.builder()
                    .amount(amount)
                    .currencyType(currencyType)
                    .billType(billType)
                    .build();
        } catch (Exception e) {
            throw new TransactionException("Transaction failed: " + e.getMessage());
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