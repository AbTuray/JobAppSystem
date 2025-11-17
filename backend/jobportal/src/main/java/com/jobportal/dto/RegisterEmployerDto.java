package com.jobportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegisterEmployerDto {
    @NotBlank private String firstName;
    @NotBlank private String lastName;
    @Email private String email;
    @NotBlank private String password;
    @Pattern(regexp = "^(077|099|078)\\d{6}$")
    private String phone;
    @NotBlank private String office;
    @NotBlank private String address;
    // getters/setters
}
