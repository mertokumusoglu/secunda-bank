package com.mert.secunda_bank.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;
    
    private Long identityNumber;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private BigDecimal balance = BigDecimal.valueOf(50.0);
    private BigDecimal loanDebt;

    @OneToMany(mappedBy = "account")
    private List<Bill> bills = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions = new ArrayList<>();

   
    private Account() {}

    public Long getAccountId() { return accountNumber; }
    public Long getIdentityNumber() { return identityNumber; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public BigDecimal getBalance() { return balance; }
    public BigDecimal getLoanDebt() { return loanDebt; }
    public List<Bill> getBills() { return bills; }
    public List<Transaction> getTransactions() { return transactions; }

    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setPassword(String password) { this.password = password; }
    public void setLoanDebt(BigDecimal loanDebt) { this.loanDebt = loanDebt; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    
    public static class Builder {
        private Account account = new Account();

        public Builder identityNumber(Long identityNumber) {
            account.identityNumber = identityNumber;
            return this;
        }

        public Builder name(String name) {
            account.name = name;
            return this;
        }

        public Builder password(String password) {
            account.password = password;
            return this;
        }

        public Builder email(String email) {
            account.email = email;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            account.phoneNumber = phoneNumber;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            account.balance = balance;
            return this;
        }

        public Builder loanDebt(BigDecimal loanDebt) {
            account.loanDebt = loanDebt;
            return this;
        }

        public Account build() {
            validateAccountData();
            return account;
        }

        private void validateAccountData() {
            StringBuilder errors = new StringBuilder();

            if (account.identityNumber == null) {
                errors.append("Identity number is required. ");
            }
            if (account.name == null) {
                errors.append("Name is required. ");
            }
            if (account.password == null) {
                errors.append("Password is required. ");
            }
            if (account.email == null) {
                errors.append("Email is required. ");
            }
            if (account.phoneNumber == null) {
                errors.append("Phone number is required. ");
            }
            if (account.balance == null) {
                account.balance = BigDecimal.valueOf(50.0);
            }
            if (account.loanDebt == null) {
                account.loanDebt = BigDecimal.ZERO;
            }
            if (errors.length() > 0) {
                throw new IllegalStateException("Invalid account data: " + errors.toString());
            }
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}

