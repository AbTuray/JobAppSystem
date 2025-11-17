// src/main/java/com/jobportal/controller/EmployerController.java
package com.jobportal.controller;

import com.jobportal.dto.ApplicationResponseDTO;
import com.jobportal.dto.JobDTO;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.service.ApplicationManager;
import com.jobportal.service.JobManager;
import com.jobportal.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/employer")
public class EmployerController {

    @Autowired
    private JobManager jobManager;

    @Autowired
    private ApplicationManager applicationManager;

    @Autowired
    private UserManager userManager;

    // === DASHBOARD ===
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        User employer = userManager.findByEmail(principal.getName());
        List<JobDTO> jobs = jobManager.getJobsByEmployer(employer);
        model.addAttribute("jobs", jobs);
        model.addAttribute("employer", employer);
        return "employer/dashboard"; // employer-dashboard.html
    }

    // === ADD JOB ===
    @GetMapping("/job/add")
    public String showAddJobForm(Model model) {
        model.addAttribute("job", new JobDTO());
        return "employer/add-job";
    }

    @PostMapping("/job/add")
    public String addJob(@ModelAttribute JobDTO jobDTO, Principal principal) {
        User employer = userManager.findByEmail(principal.getName());
        jobManager.createJob(jobDTO, employer);
        return "redirect:/employer/dashboard";
    }

    // === EDIT JOB ===
    @GetMapping("/job/edit/{id}")
    public String showEditJobForm(@PathVariable Long id, Model model, Principal principal) {
        JobDTO job = jobManager.getJobById(id);
        if (job == null) {
            return "redirect:/employer/dashboard?error=notfound";
        }
        model.addAttribute("job", job);
        return "employer/edit-job";
    }

    @PostMapping("/job/edit/{id}")
    public String updateJob(@PathVariable Long id, @ModelAttribute JobDTO jobDTO, Principal principal) {
        User employer = userManager.findByEmail(principal.getName());
        jobManager.updateJob(id, jobDTO, employer);
        return "redirect:/employer/dashboard";
    }

    // === DELETE JOB ===
    @PostMapping("/job/delete/{id}")
    public String deleteJob(@PathVariable Long id, Principal principal) {
        User employer = userManager.findByEmail(principal.getName());
        jobManager.deleteJob(id, employer);
        return "redirect:/employer/dashboard";
    }

    // === DEACTIVATE JOB ===
    @PostMapping("/job/deactivate/{id}")
    public String deactivateJob(@PathVariable Long id, Principal principal) {
        User employer = userManager.findByEmail(principal.getName());
        jobManager.deactivateJob(id, employer);
        return "redirect:/employer/dashboard";
    }

    // === VIEW APPLICANTS FOR A JOB ===
    @GetMapping("/job/{jobId}/applicants")
    public String viewApplicants(@PathVariable Long jobId, Model model, Principal principal) {
        User employer = userManager.findByEmail(principal.getName());
        Job job = new Job();
        job.setId(jobId);
        job.setEmployer(employer);

        List<ApplicationResponseDTO> applicants = applicationManager.getApprovedApplicationsByJob(job);
        model.addAttribute("applicants", applicants);
        model.addAttribute("jobId", jobId);
        return "employer/applicants";
    }

    // === ACCEPT APPLICANT ===
    @PostMapping("/applicant/accept/{appId}")
    public String acceptApplicant(@PathVariable Long appId, @RequestParam Long jobId, Principal principal) {
        User employer = userManager.findByEmail(principal.getName());
        applicationManager.acceptApplicant(appId, employer);
        return "redirect:/employer/job/" + jobId + "/applicants";
    }

    // === REMOVE APPLICANT ===
    @PostMapping("/applicant/remove/{appId}")
    public String removeApplicant(@PathVariable Long appId, @RequestParam Long jobId, Principal principal) {
        User employer = userManager.findByEmail(principal.getName());
        applicationManager.removeApplicant(appId, employer);
        return "redirect:/employer/job/" + jobId + "/applicants";
    }

    // === VIEW ALL APPROVED APPLICATIONS (across all jobs) ===
    @GetMapping("/applications")
    public String viewAllApplications(Model model, Principal principal) {
        User employer = userManager.findByEmail(principal.getName());
        List<ApplicationResponseDTO> applications = applicationManager.getApprovedApplicationsByEmployer(employer);
        model.addAttribute("applications", applications);
        return "employer/applications";
    }
}