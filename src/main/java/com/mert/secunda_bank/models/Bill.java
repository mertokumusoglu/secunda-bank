package com.mert.secunda_bank.models;

import com.mert.secunda_bank.models.enums.BillTypes;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bills")
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billId;

    @ManyToOne
    @JoinColumn(name = "accountNumber", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    private BillTypes billType;
    private BigDecimal amount;
    private CurrencyTypes currency;
    private LocalDateTime dueDate;
    private String status;

    public Bill() {
        // default
    }

    public Bill(Long billId, Account account,BillTypes billType, BigDecimal amount, LocalDateTime dueDate, CurrencyTypes currency, String status) {
        this.billId = billId;
        this.account = account;
        this.billType = billType;
        this.amount = amount;
        this.dueDate = dueDate;
        this.currency = currency;
        this.status = status;
    }

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public CurrencyTypes getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyTypes currency) {
        this.currency = currency;
    }

    public BillTypes getBillType() {
        return billType;
    }

    public void setBillType(BillTypes billType) {
        this.billType = billType;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
