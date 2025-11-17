// src/main/java/com/jobportal/controller/AdminController.java
package com.jobportal.controller;

import com.jobportal.dto.ApplicationResponseDTO;
import com.jobportal.dto.UserDTO;
import com.jobportal.service.ApplicationManager;
import com.jobportal.service.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ApplicationManager applicationManager;

    @Autowired
    private UserManager userManager;

    // === DASHBOARD ===
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<ApplicationResponseDTO> pendingApps = applicationManager.getPendingApplications();
        List<UserDTO> candidates = userManager.getAllCandidates();
        List<UserDTO> employers = userManager.getAllEmployers();

        model.addAttribute("pendingApps", pendingApps);
        model.addAttribute("candidates", candidates);
        model.addAttribute("employers", employers);
        return "admin/dashboard";
    }

    // === APPROVE APPLICATION ===
    @PostMapping("/application/approve/{id}")
    public String approveApplication(@PathVariable Long id) {
        applicationManager.approveApplication(id);
        return "redirect:/admin/dashboard";
    }

    // === REJECT APPLICATION ===
    @PostMapping("/application/reject/{id}")
    public String rejectApplication(@PathVariable Long id) {
        applicationManager.rejectApplication(id);
        return "redirect:/admin/dashboard";
    }

    // === DELETE CANDIDATE ===
    @PostMapping("/candidate/delete/{id}")
    public String deleteCandidate(@PathVariable Long id) {
        userManager.deleteUser(id);
        return "redirect:/admin/dashboard";
    }

    // === DELETE EMPLOYER ===
    @PostMapping("/employer/delete/{id}")
    public String deleteEmployer(@PathVariable Long id) {
        userManager.deleteUser(id);
        return "redirect:/admin/dashboard";
    }

    // === VIEW ALL JOBS (optional) ===
    @GetMapping("/jobs")
    public String viewAllJobs(Model model) {
        // Reuse JobManager if needed, or add method
        return "admin/jobs";
    }
}