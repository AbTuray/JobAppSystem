package com.jobportal.controller;

import com.jobportal.dto.LoginDto;
import com.jobportal.dto.RegisterEmployeeDto;
import com.jobportal.dto.RegisterEmployerDto;
import com.jobportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register/employer", method = RequestMethod.POST)
    public ResponseEntity<?> registerEmployer(@Valid @RequestBody RegisterEmployerDto dto) {
        userService.registerEmployer(dto);
        return ResponseEntity.ok("Employer registered");
    }

    @RequestMapping(value = "/register/employee", method = RequestMethod.POST)
    public ResponseEntity<?> registerEmployee(@Valid @RequestBody RegisterEmployeeDto dto) {
        userService.registerEmployee(dto);
        return ResponseEntity.ok("Employee registered");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) {
        // Spring Security will handle authentication via formLogin endpoint; here you can return success if reached
        return ResponseEntity.ok("Login endpoint (handled by Spring Security)");
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<?> logout() {
        // logout handled by Spring Security
        return ResponseEntity.ok("Logged out");
    }
}
