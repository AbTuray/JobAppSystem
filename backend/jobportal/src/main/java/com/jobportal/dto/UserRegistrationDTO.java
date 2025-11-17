// src/main/java/com/jobportal/dto/UserRegistrationDTO.java
package com.jobportal.dto;

import jakarta.validation.constraints.*;

public class UserRegistrationDTO {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Phone is required")
    @Pattern(regexp = "^(077|078|099)\\d{6}$",
            message = "Phone must start with 077, 078, or 099 followed by 6 digits")
    private String phone;

    @NotBlank(message = "Address is required")
    private String address;

    // Only for Employer
    private String office;

    // "EMPLOYER" or "CANDIDATE"
    @NotBlank(message = "User type is required")
    private String userType;

    // Constructors
    public UserRegistrationDTO() {}

    // Getters and Setters
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getOffice() { return office; }
    public void setOffice(String office) { this.office = office; }

    public String getUserType() { return userType; }
    public void setUserType(String userType) { this.userType = userType; }
}