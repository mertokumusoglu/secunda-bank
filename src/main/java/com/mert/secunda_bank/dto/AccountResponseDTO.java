package com.mert.secunda_bank.dto;

import java.math.BigDecimal;

public class AccountResponseDTO {

    private Long accountNumber;
    private Long identityNumber;
    private String name;
    private String email;
    private String phoneNumber;
    private BigDecimal balance;
    private BigDecimal loanDebt;

    // Constructors
    public AccountResponseDTO() {
    }

    public AccountResponseDTO(Long accountNumber, Long identityNumber, String name, String email, String phoneNumber, BigDecimal balance, BigDecimal loanDebt) {
        this.accountNumber = accountNumber;
        this.identityNumber = identityNumber;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.loanDebt = loanDebt;
    }

    // Getters and Setters
    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public BigDecimal getLoanDebt() {
        return loanDebt;
    }

    public void setLoanDebt(BigDecimal loanDebt) {
        this.loanDebt = loanDebt;
    }
} 