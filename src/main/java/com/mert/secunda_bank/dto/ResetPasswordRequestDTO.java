package com.mert.secunda_bank.dto;

public class ResetPasswordRequestDTO {

    private String oldPassword;
    private String newPassword;

    // Constructors
    public ResetPasswordRequestDTO() {
    }

    public ResetPasswordRequestDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    // Getters and Setters
    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
} 