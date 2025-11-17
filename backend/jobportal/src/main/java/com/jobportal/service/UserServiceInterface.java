// src/main/java/com/jobportal/service/UserServiceInterface.java
package com.jobportal.service;

import com.jobportal.dto.UserRegistrationDTO;
import com.jobportal.dto.UserDTO;
import com.jobportal.entity.User;

import java.util.List;

public interface UserServiceInterface {

    // Register new user (Employer or Candidate)
    User registerUser(UserRegistrationDTO registrationDTO);

    // Find user by email (for login)
    User findByEmail(String email);

    // Get safe DTO by email
    UserDTO getUserDTOByEmail(String email);

    // Get all candidates
    List<UserDTO> getAllCandidates();

    // Get all employers
    List<UserDTO> getAllEmployers();

    // Delete user by ID (Admin)
    void deleteUser(Long id);

    // Check if email exists
    boolean existsByEmail(String email);
}