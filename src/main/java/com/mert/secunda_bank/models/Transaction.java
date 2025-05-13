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
    private Long transactionId;

    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private CurrencyTypes currencyTypes;
    private TransactionTypes type;
    private LocalDateTime timestamp;
    private String status = "PENDING";
    private String description;
    private BigDecimal fee;


    public Transaction() {
        // default
    }

    public Transaction(BigDecimal amount, LocalDateTime timestamp, CurrencyTypes currencyTypes, TransactionTypes type, String status, String description, BigDecimal fee) {

        this.amount = amount;
        this.timestamp = timestamp.now();
        this.currencyTypes = currencyTypes;
        this.type = type;
        this.status = status;
        this.description = description;
        this.fee = fee;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyTypes getCurrency() {
        return currencyTypes;
    }

    public void setCurrency(CurrencyTypes currencyTypes) {
        this.currencyTypes = currencyTypes;
    }

    public TransactionTypes getType() {
        return type;
    }

    public void setType(TransactionTypes type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }
    public abstract void execute();
}

