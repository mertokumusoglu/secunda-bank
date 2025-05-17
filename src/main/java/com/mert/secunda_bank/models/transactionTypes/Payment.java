package com.mert.secunda_bank.models.transactionTypes;

import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.models.enums.BillTypes;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.enums.TransactionTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("PAYMENT")
public class Payment extends Transaction {
    
    protected Long accountNumber;
    
    @Enumerated(EnumType.STRING)
    protected BillTypes billType;

    // Protected constructor for JPA and Builder
    protected Payment() {
        super();
    }

    @Override
    public void execute() {
        this.setStatus("COMPLETED");
        System.out.println("Bill payment completed for: " + getBillType() + " from account: " + getAccountNumber());
    }

    // Getters
    public Long getAccountNumber() { return accountNumber; }
    public BillTypes getBillType() { return billType; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends TransactionBuilder<Payment, Builder> {
        
        public Builder() {
            transaction = new Payment();
            transaction.type = TransactionTypes.PAYMENT;
            transaction.status = "PENDING";
            transaction.timestamp = LocalDateTime.now();
            transaction.fee = new BigDecimal("1.5"); // Fatura ödemelerinde sabit işlem ücreti
            transaction.description = "Bill payment transaction";
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder accountNumber(Long accountNumber) {
            transaction.accountNumber = accountNumber;
            return this;
        }

        public Builder billType(BillTypes billType) {
            transaction.billType = billType;
            return this;
        }

        @Override
        public Payment build() {
            validatePaymentFields();
            validateCommonFields();
            return transaction;
        }

        // PAYMENT VALIDATE
        private void validatePaymentFields() {
            StringBuilder errors = new StringBuilder();

            if (transaction.accountNumber == null) {
                errors.append("Account number is required. ");
            }
            
            if (transaction.billType == null) {
                errors.append("Bill type is required. ");
            }
            if (errors.length() > 0) {
                throw new IllegalStateException("Invalid payment data: " + errors.toString());
            }
        }
    }
}
