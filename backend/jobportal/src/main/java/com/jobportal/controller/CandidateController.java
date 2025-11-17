package com.jobportal.controller;

import com.jobportal.dto.CandidateRegistrationDTO;
import com.jobportal.dto.CandidateLoginDTO;
import com.jobportal.entity.Candidate;
import com.jobportal.entity.Job;
import com.jobportal.entity.Application;
import com.jobportal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateManager candidateService;

    @Autowired
    private JobManager jobService;

    @Autowired
    private ApplicationManager applicationService;

    // Register Candidate
    @PostMapping("/register")
    public Candidate registerCandidate(@RequestBody CandidateRegistrationDTO dto) {
        return candidateService.registerCandidate(dto);
    }

    // Login Candidate
    @PostMapping("/login")
    public Candidate loginCandidate(@RequestBody CandidateLoginDTO dto) {
        return candidateService.loginCandidate(dto);
    }

    // Search Jobs
    @GetMapping("/jobs")
    public List<Job> searchJobs(@RequestParam(required = false) String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return jobService.getAllJobs();
        }
        return jobService.getAllJobs().stream()
                .filter(j -> j.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    // Apply for Job
    @PostMapping("/apply")
    public Application applyForJob(@RequestParam Long candidateId, @RequestParam Long jobId) {
        Candidate candidate = candidateService.getCandidateById(candidateId);
        Job job = jobService.getJobById(jobId);
        return applicationService.applyForJob(candidate, job);
    }

    // View Applications
    @GetMapping("/applications")
    public List<Application> viewApplications(@RequestParam Long candidateId) {
        Candidate candidate = candidateService.getCandidateById(candidateId);
        return applicationService.getApplicationsByCandidate(candidate);
    }

    // Delete Application
    @DeleteMapping("/application/{id}")
    public String deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return "Application deleted successfully";
    }

    @GetMapping("/job/{jobId}/applications")
    public List<Application> getApplicationsByJob(@PathVariable Long jobId) {
        Job job = jobService.getJobById(jobId);
        return applicationService.getApplicationsByJob(job);
    }

    // Update application status (accept applicant)
    @PutMapping("/application/{id}/status")
    public Application updateApplicationStatus(@PathVariable Long id, @RequestParam String status) {
        return applicationService.updateApplicationStatus(id, status);
    }
}
