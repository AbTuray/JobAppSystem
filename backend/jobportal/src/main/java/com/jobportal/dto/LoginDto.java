package com.jobportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDto {
    @Email
    private String email;
    @NotBlank
    private String password;
    // getters/setters
}
