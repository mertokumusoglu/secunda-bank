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
import jakarta.transaction.TransactionalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountService accountService;


    public void withdrawal(Long senderAccountNumber, BigDecimal amount, CurrencyTypes currencyType) {
        Account account = accountService.getAccountByAccountNumber(senderAccountNumber);
        if(hasValidateSufficientFunds(account, amount)) {
            Withdrawal withdrawal = TransactionFactory.createWithdrawalTransaction(senderAccountNumber, amount, currencyType);
            transactionRepository.save(withdrawal);
            try {
                // money goes from bank to user's hand - bank's itself will be added
                debitAccount(account, amount);
                transactionRepository.save(withdrawal);
                withdrawal.execute();
            } catch (Exception e) {
                withdrawal.setStatus("FAILED");
                throw new TransactionalException("Transaction failed", e);
            }
        }
    }

    public void transfer(BigDecimal amount, CurrencyTypes currencyTypes, Long receiverAccountNumber, Long senderAccountNumber) {
        Account senderAccount = accountService.getAccountByAccountNumber(senderAccountNumber);
        Account receiverAccount = accountService.getAccountByAccountNumber(receiverAccountNumber);
        if(hasValidateSufficientFunds(senderAccount, amount)) {
            Transfer transfer = TransactionFactory.createTransferTransaction(amount, currencyTypes, receiverAccountNumber, senderAccountNumber);
            transactionRepository.save(transfer);
            try {
                debitAccount(senderAccount, amount);
                // money goes to bank balance then receiver
                creditAccount(receiverAccount, amount);
                transactionRepository.save(transfer);
                transfer.execute();
            } catch (Exception e) {
                transfer.setStatus("FAILED");
                throw new TransactionalException("Transaction failed", e);
            }
        }
    }

    public void deposit(BigDecimal amount, CurrencyTypes currencyTypes, Long receiverAccountNumber) {
        Account receiverAccount = accountService.getAccountByAccountNumber(receiverAccountNumber);
        if(hasValidateSufficientFunds(receiverAccount, amount)) {
            Deposit deposit = TransactionFactory.createDepositTransaction(amount, currencyTypes, receiverAccountNumber);
            transactionRepository.save(deposit);
            try {
                //first - money goes to pool from the bank then to user
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
    private boolean isExistReceiverAccount(Long receiverAccountNumber) {
        return accountService.getAccountByAccountNumber(receiverAccountNumber) != null;
    }
    private void debitAccount(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }
    private void creditAccount(Account account, BigDecimal amount) {
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }
}
