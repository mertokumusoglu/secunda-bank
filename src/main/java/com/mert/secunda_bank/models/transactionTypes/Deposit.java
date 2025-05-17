package com.mert.secunda_bank.models.transactionTypes;

import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.enums.TransactionTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("DEPOSIT")
public class Deposit extends Transaction {
    
    protected Long accountNumber;

    // Protected constructor for JPA and Builder
    protected Deposit() {
        super();
    }

    @Override
    public void execute() {
        this.setStatus("COMPLETED");
        System.out.println("Deposit completed to account: " + getAccountNumber());
    }

    // Getter
    public Long getAccountNumber() { return accountNumber; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends TransactionBuilder<Deposit, Builder> {
        
        public Builder() {
            transaction = new Deposit();
            transaction.type = TransactionTypes.DEPOSIT;
            transaction.status = "PENDING";
            transaction.timestamp = LocalDateTime.now();
            transaction.fee = BigDecimal.ZERO;
            transaction.description = "Deposit transaction";
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
        public Deposit build() {
            validateDepositFields();
            validateCommonFields();
            return transaction;
        }

        private void validateDepositFields() {
            StringBuilder errors = new StringBuilder();

            if (transaction.accountNumber == null) {
                errors.append("Account number is required. ");
            }
            if (errors.length() > 0) {
                throw new IllegalStateException("Invalid deposit data: " + errors.toString());
            }
        }
    }
}
