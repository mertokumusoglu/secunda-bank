package com.mert.secunda_bank.models.transactionTypes;

import com.mert.secunda_bank.models.Transaction;
import com.mert.secunda_bank.models.enums.CurrencyTypes;
import com.mert.secunda_bank.models.enums.TransactionTypes;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("TRANSFER")
public class Transfer extends Transaction {
    
    protected Long senderAccountNumber;
    protected Long receiverAccountNumber;

    protected Transfer() {
        super();
    }

    @Override
    public void execute() {
        // after transfer code in service layer
        this.setStatus("COMPLETED");
        System.out.println("Transfer completed to " + getReceiverAccountNumber() + " by " + getSenderAccountNumber());
    }

    // Getters
    public Long getSenderAccountNumber() { return senderAccountNumber; }
    public Long getReceiverAccountNumber() { return receiverAccountNumber; }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends TransactionBuilder<Transfer, Builder> {
        
        public Builder() {
            transaction = new Transfer();
            transaction.type = TransactionTypes.TRANSFER;
            transaction.status = "PENDING";
            transaction.timestamp = LocalDateTime.now();
            transaction.fee = BigDecimal.ZERO;
            transaction.description = "Transfer transaction";
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder sender(Long senderAccountNumber) {
            transaction.senderAccountNumber = senderAccountNumber;
            return this;
        }

        public Builder receiver(Long receiverAccountNumber) {
            transaction.receiverAccountNumber = receiverAccountNumber;
            return this;
        }

        @Override
        public Transfer build() {
            validateTransferFields();
            validateCommonFields();
            return transaction;
        }

        private void validateTransferFields() {
            StringBuilder errors = new StringBuilder();

            if (transaction.senderAccountNumber == null) {
                errors.append("Sender account number is required. ");
            }
            if (transaction.receiverAccountNumber == null) {
                errors.append("Receiver account number is required. ");
            }
            if (transaction.senderAccountNumber != null && 
                transaction.receiverAccountNumber != null && 
                transaction.senderAccountNumber.equals(transaction.receiverAccountNumber)) {
                errors.append("Sender and receiver accounts cannot be the same. ");
            }

            if (errors.length() > 0) {
                throw new IllegalStateException("Invalid transfer data: " + errors.toString());
            }
        }
    }
}
