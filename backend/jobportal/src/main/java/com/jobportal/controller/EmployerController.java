package com.jobportal.controller;

import com.jobportal.dto.EmployerRegistrationDTO;
import com.jobportal.dto.EmployerLoginDTO;
import com.jobportal.entity.Employer;
import com.jobportal.service.EmployerManager;
import com.jobportal.service.JobManager;
import com.jobportal.entity.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    private EmployerManager employerService;

    @Autowired
    private JobManager jobService;

    // Register Employer
    @PostMapping("/register")
    public Employer registerEmployer(@RequestBody EmployerRegistrationDTO dto) {
        return employerService.registerEmployer(dto);
    }

    // Login Employer
    @PostMapping("/login")
    public Employer loginEmployer(@RequestBody EmployerLoginDTO dto) {
        return employerService.loginEmployer(dto);
    }

    // Add Job
    @PostMapping("/job")
    public Job addJob(@RequestBody Job job, Authentication authentication) {
        Employer employer = getLoggedInEmployer(authentication);
        job.setEmployer(employer);
        return jobService.addJob(job);
    }

    // Update Job
    @PutMapping("/job/{id}")
    public Job updateJob(@PathVariable Long id, @RequestBody Job job, Authentication authentication) {
        Employer employer = getLoggedInEmployer(authentication);
        job.setId(id);
        job.setEmployer(employer); // ensure employer is set
        return jobService.updateJob(job);
    }

    // Delete Job
    @DeleteMapping("/job/{id}")
    public String deleteJob(@PathVariable Long id, Authentication authentication) {
        Employer employer = getLoggedInEmployer(authentication);
        jobService.deleteJobByEmployer(id, employer);
        return "Job deleted successfully";
    }

    // Get Jobs for Logged-in Employer
    @GetMapping("/jobs")
    public List<Job> getJobs(Authentication authentication) {
        Employer employer = getLoggedInEmployer(authentication);
        return jobService.getJobsByEmployer(employer);
    }

    // Helper to fetch logged-in employer
    private Employer getLoggedInEmployer(Authentication authentication) {
        String email = authentication.getName();
        return employerService.getEmployerByEmail(email);
    }

//    // Add Job
//    @PostMapping("/job")
//    public Job addJob(@RequestBody Job job) {
//        return jobService.addJob(job);
//    }
////
////    @PostMapping("/job")
////    public Job addJob(@RequestBody Job job, Authentication authentication) {
////        String email = authentication.getName(); // get logged-in user's email
////        Employer emp = employerService.findByEmail(email); // fetch employer
////        job.setEmployer(emp); // assign employer to job
////        return jobService.addJob(job);
////    }
//
//
//
//
//    // Edit Job
//    @PutMapping("/job/{id}")
//    public Job updateJob(@PathVariable Long id, @RequestBody Job job) {
//        job.setId(id);
//        return jobService.updateJob(job);
//    }
//// Update job
////@PutMapping("/job/{id}")
////public Job updateJob(@PathVariable Long id, @RequestBody Job job,
////                     @AuthenticationPrincipal UserDetails userDetails) {
////    Employer employer = employerService.getEmployerByEmail(userDetails.getUsername());
////    Job existingJob = jobService.getJobById(id);
////
////    if (!existingJob.getEmployer().getId().equals(employer.getId())) {
////        throw new RuntimeException("You can only update your own jobs");
////    }
////
////    job.setId(id);
////    job.setEmployer(employer);
////    return jobService.updateJob(job);
////}
//
//    // Delete Job
//    @DeleteMapping("/job/{id}")
//    public String deleteJob(@PathVariable Long id) {
//        jobService.deleteJob(id);
//        return "Job deleted successfully";
//    }
//// Delete job
////@DeleteMapping("/job/{id}")
////public String deleteJob(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
////    Employer employer = employerService.getEmployerByEmail(userDetails.getUsername());
////    Job job = jobService.getJobById(id);
////
////    if (!job.getEmployer().getId().equals(employer.getId())) {
////        throw new RuntimeException("You can only delete your own jobs");
////    }
////
////    jobService.deleteJob(id);
////    return "Job deleted successfully";
////}
//
//    // View all jobs by employer
//    @GetMapping("/jobs")
//    public List<Job> getJobs(@RequestParam Long employerId) {
//        Employer employer = employerService.getEmployerById(employerId);
//        return jobService.getJobsByEmployer(employer);
//    }
////    @GetMapping("/jobs")
////    public List<Job> getJobs(Authentication authentication) {
////        String email = authentication.getName();
////        Employer employer = employerService.findByEmail(email);
////        return jobService.getJobsByEmployer(employer);
////    }

}
