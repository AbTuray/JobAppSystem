package com.jobportal.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class EmployerRegistrationDTO {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Pattern(regexp = "^(077|078|099)\\d{6}$", message = "Phone must start with 077, 078, or 099 and have 6 digits after")
    private String phone;

    @NotBlank
    private String office;

    @NotBlank
    private String address;

    @NotBlank
    private String password;

    public EmployerRegistrationDTO() {
    }

    public EmployerRegistrationDTO(String password, String address, String office, String phone, String email, String lastName, String firstName) {
        this.password = password;
        this.address = address;
        this.office = office;
        this.phone = phone;
        this.email = email;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    // Getters and Setters

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
