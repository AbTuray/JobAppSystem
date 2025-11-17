// src/main/java/com/jobportal/service/UserManager.java
package com.jobportal.service;

import com.jobportal.dto.UserDTO;
import com.jobportal.dto.UserRegistrationDTO;
import com.jobportal.entity.User;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserManager implements UserServiceInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserRegistrationDTO registrationDTO) {
        // Check if email already exists
        if (userRepository.findByEmail(registrationDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setFirstName(registrationDTO.getFirstName());
        user.setLastName(registrationDTO.getLastName());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setPhone(registrationDTO.getPhone());
        user.setAddress(registrationDTO.getAddress());
        user.setUserType(registrationDTO.getUserType());

        // Office is only for EMPLOYER
        if ("EMPLOYER".equals(registrationDTO.getUserType())) {
            user.setOffice(registrationDTO.getOffice());
        } else {
            user.setOffice(null);
        }

        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        return userOpt.orElse(null);
    }

    @Override
    public UserDTO getUserDTOByEmail(String email) {
        User user = findByEmail(email);
        if (user == null) {
            return null;
        }
        return mapToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllCandidates() {
        return userRepository.findAllCandidates()
                .stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllEmployers() {
        return userRepository.findAllEmployers()
                .stream()
                .map(this::mapToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    // Helper: Entity -> DTO
    private UserDTO mapToUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setUserType(user.getUserType());
        dto.setOffice(user.getOffice());
        return dto;
    }
}