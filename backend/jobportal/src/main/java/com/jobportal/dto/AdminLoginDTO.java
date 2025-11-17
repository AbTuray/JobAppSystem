package com.jobportal.dto;

import jakarta.validation.constraints.NotBlank;

public class AdminLoginDTO {

    @NotBlank
    private String username; // Hardcoded

    @NotBlank
    private String password; // Hardcoded

    public AdminLoginDTO() {
    }

    public AdminLoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
