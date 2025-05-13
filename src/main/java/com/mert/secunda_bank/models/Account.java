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

    public Account() {
        // Default constructor
    }

    public Account(Long identityNumber, String name,String password, String email, String phoneNumber, BigDecimal balance, BigDecimal loanDebt ) {
        this.identityNumber = identityNumber;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.loanDebt = loanDebt;
    }

    public Long getAccountId() {
        return accountNumber;
    }

    public void setAccountId(Long accountId) {
        this.accountNumber = accountId;
    }

    public Long getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(Long identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getLoanDebt() {
        return loanDebt;
    }

    public void setLoanDebt(BigDecimal loanDebt) {
        this.loanDebt = loanDebt;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}

