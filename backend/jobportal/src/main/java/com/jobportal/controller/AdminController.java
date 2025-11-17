package com.jobportal.controller;

import com.jobportal.enums.ApplicationStatus;
import com.jobportal.service.*;
import com.jobportal.entity.Candidate;
import com.jobportal.entity.Employer;
import com.jobportal.entity.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminManager adminService;

    @Autowired
    private EmployerManager employerService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private ApplicationService applicationService;

    // Admin Login
    @PostMapping("/login")
    public String adminLogin(@RequestParam String username, @RequestParam String password) {
        boolean success = adminService.login(username, password);
        if(success) return "Admin logged in successfully";
        else return "Invalid credentials";
    }

    // View all employers
    @GetMapping("/employers")
    public List<Employer> viewAllEmployers() {
        return employerService.getAllEmployers();
    }

    // View all candidates
    @GetMapping("/candidates")
    public List<Candidate> viewAllCandidates() {
        return candidateService.getAllCandidates();
    }

    // Delete employer
    @DeleteMapping("/employer/{id}")
    public String deleteEmployer(@PathVariable Long id) {
        employerService.deleteEmployer(id);
        return "Employer deleted successfully";
    }

    // Delete candidate
    @DeleteMapping("/candidate/{id}")
    public String deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return "Candidate deleted successfully";
    }

    // View applications by status (Pending)
    @GetMapping("/applications")
    public List<Application> viewApplications(@RequestParam(defaultValue = "Pending") ApplicationStatus status) {
        return applicationService.getApplicationsByStatus(status);
    }

    // Approve application
    @PutMapping("/approve/{id}")
    public Application approveApplication(@PathVariable Long id) {
        return applicationService.updateApplicationStatus(id, "Approved");
    }
}
