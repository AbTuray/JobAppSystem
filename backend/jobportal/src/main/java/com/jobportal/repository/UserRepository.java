// src/main/java/com/jobportal/repository/UserRepository.java
package com.jobportal.repository;

import com.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (for login)
    Optional<User> findByEmail(String email);

    // Find all employers
    List<User> findByUserType(String userType);

    // Custom query: Find all candidates
    @Query("SELECT u FROM User u WHERE u.userType = 'CANDIDATE'")
    List<User> findAllCandidates();

    // Custom query: Find all employers
    @Query("SELECT u FROM User u WHERE u.userType = 'EMPLOYER'")
    List<User> findAllEmployers();
}