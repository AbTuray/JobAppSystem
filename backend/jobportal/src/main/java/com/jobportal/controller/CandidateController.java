// src/main/java/com/jobportal/controller/CandidateController.java
package com.jobportal.controller;

import com.jobportal.dto.ApplicationDTO;
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
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private JobManager jobManager;

    @Autowired
    private ApplicationManager applicationManager;

    @Autowired
    private UserManager userManager;

    // === DASHBOARD ===
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {
        User candidate = userManager.findByEmail(principal.getName());
        List<ApplicationResponseDTO> applications = applicationManager.getApplicationsByCandidate(candidate);
        model.addAttribute("applications", applications);
        model.addAttribute("candidate", candidate);
        return "candidate/dashboard";
    }

    // === SEARCH JOBS ===
    @GetMapping("/jobs")
    public String searchJobs(@RequestParam(required = false) String keyword, Model model) {
        List<JobDTO> jobs = (keyword == null || keyword.isEmpty())
                ? jobManager.getActiveJobs()
                : jobManager.searchJobs(keyword);
        model.addAttribute("jobs", jobs);
        model.addAttribute("keyword", keyword);
        return "candidate/jobs";
    }

    // === APPLY TO JOB ===
    @GetMapping("/job/apply/{jobId}")
    public String showApplyForm(@PathVariable Long jobId, Model model, Principal principal) {
        JobDTO jobDTO = jobManager.getJobById(jobId);
        if (jobDTO == null || !jobDTO.isActive()) {
            return "redirect:/candidate/jobs?error=invalid";
        }

        User candidate = userManager.findByEmail(principal.getName());

        // CORRECT WAY: Create Job object and set ID manually
        Job job = new Job();
        job.setId(jobId);

        boolean hasApplied = applicationManager.hasApplied(candidate, job);
        if (hasApplied) {
            return "redirect:/candidate/jobs?error=alreadyapplied";
        }

        model.addAttribute("job", jobDTO);
        model.addAttribute("application", new ApplicationDTO());
        return "candidate/apply";
    }

    @PostMapping("/job/apply/{jobId}")
    public String applyToJob(@PathVariable Long jobId, @ModelAttribute ApplicationDTO applicationDTO, Principal principal) {
        applicationDTO.setJobId(jobId);
        User candidate = userManager.findByEmail(principal.getName());
        applicationManager.applyToJob(applicationDTO, candidate);
        return "redirect:/candidate/dashboard?applied=true";
    }

    // === EDIT APPLICATION ===
    @GetMapping("/application/edit/{appId}")
    public String showEditApplication(@PathVariable Long appId, Model model, Principal principal) {
        User candidate = userManager.findByEmail(principal.getName());
        // We'll fetch via service to validate ownership
        List<ApplicationResponseDTO> apps = applicationManager.getApplicationsByCandidate(candidate);
        ApplicationResponseDTO app = apps.stream()
                .filter(a -> a.getId().equals(appId))
                .findFirst()
                .orElse(null);

        if (app == null || !"PENDING".equals(app.getStatus())) {
            return "redirect:/candidate/dashboard?error=invalid";
        }

        model.addAttribute("application", app);
        return "candidate/edit-application";
    }

    @PostMapping("/application/edit/{appId}")
    public String updateApplication(@PathVariable Long appId, @RequestParam String coverLetter, Principal principal) {
        User candidate = userManager.findByEmail(principal.getName());
        applicationManager.updateApplication(appId, coverLetter, candidate);
        return "redirect:/candidate/dashboard";
    }

    // === DELETE APPLICATION ===
    @PostMapping("/application/delete/{appId}")
    public String deleteApplication(@PathVariable Long appId, Principal principal) {
        User candidate = userManager.findByEmail(principal.getName());
        applicationManager.deleteApplication(appId, candidate);
        return "redirect:/candidate/dashboard";
    }
}