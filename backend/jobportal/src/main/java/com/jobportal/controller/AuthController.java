package com.jobportal.controller;

import com.jobportal.dto.CandidateRegistrationDTO;
import com.jobportal.dto.EmployerRegistrationDTO;
import com.jobportal.dto.LoginDTO;
import com.jobportal.entity.Candidate;
import com.jobportal.entity.Employer;
import com.jobportal.security.JwtUtil;
import com.jobportal.service.AdminManager;
import com.jobportal.service.CandidateManager;
import com.jobportal.service.EmployerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmployerManager employerService;

    @Autowired
    private CandidateManager candidateService;

    @Autowired
    private AdminManager adminService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // <-- Autowire it here

    @PostMapping("/register/employer")
    public ResponseEntity<?> registerEmployer(@RequestBody EmployerRegistrationDTO dto) {
        return ResponseEntity.ok(employerService.registerEmployer(dto));
    }

    @PostMapping("/register/candidate")
    public ResponseEntity<?> registerCandidate(@RequestBody CandidateRegistrationDTO dto) {
        return ResponseEntity.ok(candidateService.registerCandidate(dto));
    }

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {

        // ADMIN LOGIN (HARD CODED)
        if (dto.getEmail().equals("admin@job.com") && dto.getPassword().equals("admin123")) {

            String token = jwtUtil.generateToken("admin@job.com", "ADMIN");

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", "ADMIN",
                    "id", 0
            ));
        }

        // EMPLOYER LOGIN
        Employer emp = employerService.findByEmail(dto.getEmail());
        if (emp != null && passwordEncoder.matches(dto.getPassword(), emp.getPassword())) {

            String token = jwtUtil.generateToken(emp.getEmail(), "EMPLOYER");

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", "EMPLOYER",
                    "id", emp.getId()
            ));
        }

        // CANDIDATE LOGIN
        Candidate cand = candidateService.findByEmail(dto.getEmail());
        if (cand != null && passwordEncoder.matches(dto.getPassword(), cand.getPassword())) {

            String token = jwtUtil.generateToken(cand.getEmail(), "CANDIDATE");

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", "CANDIDATE",
                    "id", cand.getId()
            ));
        }

        return ResponseEntity.status(401).body("Invalid Credentials");
    }


//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody LoginDTO dto) {
//
//        // ADMIN HARD-CODED
//        if (dto.getEmail().equals("admin@job.com") && dto.getPassword().equals("admin123")) {
//            return ResponseEntity.ok(Map.of("role", "ADMIN", "id", 0));
//        }
//
//        // Employer login
//        Employer emp = employerService.findByEmail(dto.getEmail()); // <-- Use findByEmail
//        if (emp != null && passwordEncoder.matches(dto.getPassword(), emp.getPassword())) {
//            return ResponseEntity.ok(Map.of("role", "EMPLOYER", "id", emp.getId()));
//        }
//
//        // Candidate login
//        Candidate cand = candidateService.findByEmail(dto.getEmail()); // <-- Use findByEmail
//        if (cand != null && passwordEncoder.matches(dto.getPassword(), cand.getPassword())) {
//            return ResponseEntity.ok(Map.of("role", "CANDIDATE", "id", cand.getId()));
//        }
//
//        return ResponseEntity.status(401).body("Invalid Credentials");
//    }
}
