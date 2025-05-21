package com.mert.secunda_bank.dto;

// validation will be added but not now
public class CreateAccountRequestDTO {

    private Long identityNumber;
    private String name;
    private String password;
    private String email;
    private String phoneNumber;

    // Constructors
    public CreateAccountRequestDTO() {
    }

    public CreateAccountRequestDTO(Long identityNumber, String name, String password, String email, String phoneNumber) {
        this.identityNumber = identityNumber;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
} 