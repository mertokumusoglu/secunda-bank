package com.mert.secunda_bank.models;

import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.enums.TransactionTypes;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "transaction_type", discriminatorType = DiscriminatorType.STRING)
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long transactionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referenced_account_id")
    protected Account account;

    protected BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    protected CurrencyTypes currencyTypes;
    
    protected TransactionTypes type;
    protected LocalDateTime timestamp;
    protected String status = "PENDING";
    protected String description;
    protected BigDecimal fee;

    protected Transaction() {}

    // Getters
    public Long getTransactionId() { return transactionId; }
    public BigDecimal getAmount() { return amount; }
    public CurrencyTypes getCurrency() { return currencyTypes; }
    public TransactionTypes getType() { return type; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getStatus() { return status; }
    public String getDescription() { return description; }
    public BigDecimal getFee() { return fee; }
    public Account getAccount() { return account; }

    // Setter for status update
    public void setStatus(String status) { this.status = status; }
    public void setAccount(Account account) { this.account = account; }

    // Abstract builder class for all transaction types
    protected abstract static class TransactionBuilder<T extends Transaction, B extends TransactionBuilder<T, B>> {
        protected T transaction;

        protected abstract B self();

        public B amount(BigDecimal amount) {
            transaction.amount = amount;
            return self();
        }

        public B currencyType(CurrencyTypes currencyType) {
            transaction.currencyTypes = currencyType;
            return self();
        }

        public B type(TransactionTypes type) {
            transaction.type = type;
            return self();
        }

        public B description(String description) {
            transaction.description = description;
            return self();
        }

        public B fee(BigDecimal fee) {
            transaction.fee = fee;
            return self();
        }

        public B account(Account account) {
            transaction.account = account;
            return self();
        }

        protected void validateCommonFields() {
            StringBuilder errors = new StringBuilder();

            if (transaction.amount == null || transaction.amount.compareTo(BigDecimal.ZERO) <= 0) {
                errors.append("Amount must be positive. ");
            }
            if (transaction.currencyTypes == null) {
                errors.append("Currency type is required. ");
            }
            if (transaction.type == null) {
                errors.append("Transaction type is required. ");
            }
            if (transaction.account == null) {
                errors.append("Account is required for transaction. ");
            }

            if (transaction.timestamp == null) {
                transaction.timestamp = LocalDateTime.now();
            }
            if (transaction.fee == null) {
                transaction.fee = BigDecimal.ZERO;
            }
            if (transaction.status == null) {
                transaction.status = "PENDING";
            }

            if (errors.length() > 0) {
                throw new IllegalStateException("Invalid transaction data: " + errors.toString());
            }
        }

        public abstract T build();
    }

    public abstract void execute();
}

