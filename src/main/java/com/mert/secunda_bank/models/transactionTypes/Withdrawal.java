package com.mert.secunda_bank.models.transactionTypes;

import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.models.enums.TransactionTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("WITHDRAWAL")
public class Withdrawal extends Transaction {
    
    protected Long accountNumber;

    // Protected constructor for JPA and Builder
    protected Withdrawal() {
        super();
    }

    @Override
    public void execute() {
        this.setStatus("COMPLETED");
        System.out.println("Withdrawal completed from account: " + getAccountNumber());
    }

    // Getter
    public Long getAccountNumber() { return accountNumber; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends TransactionBuilder<Withdrawal, Builder> {
        
        public Builder() {
            transaction = new Withdrawal();
            transaction.type = TransactionTypes.WITHDRAWAL;
            transaction.status = "PENDING";
            transaction.timestamp = LocalDateTime.now();
            transaction.fee = BigDecimal.ZERO;
            transaction.description = "Withdrawal transaction";
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder accountNumber(Long accountNumber) {
            transaction.accountNumber = accountNumber;
            return this;
        }

        @Override
        public Withdrawal build() {
            validateWithdrawalFields();
            validateCommonFields();
            return transaction;
        }

        private void validateWithdrawalFields() {
            StringBuilder errors = new StringBuilder();

            if (transaction.accountNumber == null) {
                errors.append("Account number is required. ");
            }

            if (transaction.amount == null || transaction.amount.compareTo(BigDecimal.ZERO) <= 0) {
                errors.append("Withdrawal amount must be positive and not null. ");
            }

            if (errors.length() > 0) {
                throw new IllegalStateException("Invalid withdrawal data: " + errors.toString());
            }
        }
    }
}
