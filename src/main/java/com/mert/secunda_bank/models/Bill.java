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

    // Protected constructor for JPA and Builder
    protected Bill() {
    }

    // Getters
    public Long getBillId() { return billId; }
    public Account getAccount() { return account; }
    public BillTypes getBillType() { return billType; }
    public BigDecimal getAmount() { return amount; }
    public CurrencyTypes getCurrency() { return currency; }
    public LocalDateTime getDueDate() { return dueDate; }
    public String getStatus() { return status; }

    // setter for status update
    public void setStatus(String status) { 
        this.status = status; 
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Bill bill = new Bill();

        public Builder account(Account account) {
            bill.account = account;
            return this;
        }

        public Builder billType(BillTypes billType) {
            bill.billType = billType;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            bill.amount = amount;
            return this;
        }

        public Builder currency(CurrencyTypes currency) {
            bill.currency = currency;
            return this;
        }

        public Builder dueDate(LocalDateTime dueDate) {
            bill.dueDate = dueDate;
            return this;
        }

        public Builder status(String status) {
            bill.status = status;
            return this;
        }

        public Bill build() {
            validateBillData();
            return bill;
        }

        private void validateBillData() {
            StringBuilder errors = new StringBuilder();

            if (bill.account == null) {
                errors.append("Account is required. ");
            }
            if (bill.billType == null) {
                errors.append("Bill type is required. ");
            }
            if (bill.amount == null || bill.amount.compareTo(BigDecimal.ZERO) <= 0) {
                errors.append("Amount must be positive. ");
            }
            if (bill.currency == null) {
                errors.append("Currency is required. ");
            }
            if (bill.dueDate == null) {
                errors.append("Due date is required. ");
            }
            
            // Set default status if not provided
            if (bill.status == null) {
                bill.status = "PENDING";
            }

            if (errors.length() > 0) {
                throw new IllegalStateException("Invalid bill data: " + errors.toString());
            }
        }
    }
}
