package com.mert.secunda_bank.dto;

public class AuthResponseDTO {
    private String token;

    public AuthResponseDTO(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }

    // Setter (opsiyonel, genellikle constructor yeterli olur)
    public void setToken(String token) {
        this.token = token;
    }
} 