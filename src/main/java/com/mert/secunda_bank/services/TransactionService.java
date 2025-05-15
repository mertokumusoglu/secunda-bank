package com.mert.secunda_bank.services;

import com.mert.secunda_bank.models.Account;
import com.mert.secunda_bank.models.Bill;
import com.mert.secunda_bank.models.enums.BillTypes;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.transactionTypes.Deposit;
import com.mert.secunda_bank.models.transactionTypes.Payment;
import com.mert.secunda_bank.models.transactionTypes.Transfer;
import com.mert.secunda_bank.models.transactionTypes.Withdrawal;
import com.mert.secunda_bank.repositories.AccountRepository;
import com.mert.secunda_bank.repositories.TransactionRepository;

import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.TransactionalException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository,
            AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
        this.accountService = accountService;
    }

    @Transactional(timeout = 10)
    public void withdrawal(Long senderAccountNumber, BigDecimal amount, CurrencyTypes currencyType) {
        Account account = accountService.getAccountByAccountNumber(senderAccountNumber);
        if (hasValidateSufficientFunds(account, amount)) {
            try {
                Withdrawal withdrawal = TransactionFactory.createWithdrawalTransaction(senderAccountNumber, amount,
                        currencyType);
                // money goes from bank to user's hand - bank's itself will be added
                BigDecimal newBalance = account.getBalance().subtract(amount);
                account.setBalance(newBalance);
                accountRepository.save(account); // ask
                transactionRepository.save(withdrawal);
                withdrawal.execute();
            } catch (Exception e) {
                throw new TransactionalException("Transaction failed", e);
            }
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transfer(BigDecimal amount, CurrencyTypes currencyTypes, Long receiverAccountNumber, Long senderAccountNumber) {

        Long firstLock = Math.min(senderAccountNumber, receiverAccountNumber);
        Long secondLock = Math.max(senderAccountNumber, receiverAccountNumber);

        Account firstAccount = accountService.getAccountByAccountNumber(firstLock);
        Account secondAccount = accountService.getAccountByAccountNumber(secondLock);

        Account senderAccount = (firstLock == senderAccountNumber) ? firstAccount : secondAccount;
        Account receiverAccount = (firstLock == senderAccountNumber) ? secondAccount : firstAccount;

        try {
            validateTransfer(senderAccount, receiverAccount, amount);
            if (hasValidateSufficientFunds(senderAccount, amount)) {
                performTransfer(senderAccount, receiverAccount, amount);
            } else {
                throw new InvalidTransactionException("Insufficient funds");
            }
        } catch (InvalidTransactionException e) {
            throw new TransactionalException("Transaction failed", e);
        }
    }

    public void deposit(BigDecimal amount, CurrencyTypes currencyTypes, Long receiverAccountNumber) {
        Account receiverAccount = accountService.getAccountByAccountNumber(receiverAccountNumber);
        if (hasValidateSufficientFunds(receiverAccount, amount)) {
            Deposit deposit = TransactionFactory.createDepositTransaction(amount, currencyTypes, receiverAccountNumber);
            transactionRepository.save(deposit);
            try {
                // first - money goes to pool from the bank then to user
                creditAccount(receiverAccount, amount);
                transactionRepository.save(deposit);
                deposit.execute();
            } catch (Exception e) {
                deposit.setStatus("FAILED");
                throw new TransactionalException("Transaction failed", e);
            }
        }
    }

    public void payment(BigDecimal amount, CurrencyTypes currencyTypes, BillTypes billTypes, Long senderAccountNumber) {
        Account account = accountService.getAccountByAccountNumber(senderAccountNumber);
        Bill bill = account.getBills().stream().filter(b -> b.getBillType() == billTypes).findFirst().get();
        if (hasValidateSufficientFunds(account, bill.getAmount())) {
            Payment payment = TransactionFactory.createPaymentTransaction(bill.getAmount(), currencyTypes, billTypes);
            transactionRepository.save(payment);

            try {
                debitAccount(account, bill.getAmount());
                bill.setStatus("PAID");
                payment.setStatus("SUCCESS");
                transactionRepository.save(payment);
                payment.execute();
            } catch (Exception e) {
                payment.setStatus("FAILED");
                throw new TransactionalException("Bill payment failed", e);
            }
        } else {
            throw new RuntimeException("Insufficient funds for bill payment");
        }

    }

    // side methods
    private boolean hasValidateSufficientFunds(Account account, BigDecimal amount) {
        return account.getBalance().compareTo(amount) >= 0;
    }

    private void debitAccount(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    private void creditAccount(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    private void performTransfer(Account senderAccount, Account receiverAccount, BigDecimal amount) {
        BigDecimal originalSenderBalance = senderAccount.getBalance();
        BigDecimal originalReceiverBalance = receiverAccount.getBalance();

        try {
            debitAccount(senderAccount, amount);
            creditAccount(receiverAccount, amount);
        } catch (Exception e) {
            senderAccount.setBalance(originalSenderBalance);
            receiverAccount.setBalance(originalReceiverBalance);
            accountRepository.save(senderAccount);
            accountRepository.save(receiverAccount);
            throw new TransactionalException("Transaction failed", e);
        }
    }

    private void validateTransfer(Account senderAccount, Account receiverAccount, BigDecimal amount) throws InvalidTransactionException {
        if (senderAccount.equals(receiverAccount)) {
            throw new InvalidTransactionException("Cannot transfer to same account");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionException("Amount must be positive");
        }
    }
}