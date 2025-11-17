// src/main/java/com/jobportal/controller/AuthController.java
package com.jobportal.controller;

import com.jobportal.dto.UserRegistrationDTO;
import com.jobportal.entity.User;
import com.jobportal.service.UserManager;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping
public class AuthController {

    @Autowired
    private UserManager userManager;

    // === HOME PAGE ===
    @GetMapping("/")
    public String home() {
        return "home"; // home.html - MainDashboard
    }

    // === REGISTER PAGE ===
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRegistrationDTO());
        return "register"; // register.html
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") UserRegistrationDTO registrationDTO,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            userManager.registerUser(registrationDTO);
            model.addAttribute("success", "Registration successful! Please login.");
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    // === LOGIN PAGE ===
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // login.html
    }

    // === REDIRECT AFTER LOGIN BASED ON USER TYPE ===
    @GetMapping("/redirectDashboard")
    public String redirectAfterLogin(Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userManager.findByEmail(principal.getName());
        if (user == null) {
            return "redirect:/login?error";
        }

        return switch (user.getUserType()) {
            case "CANDIDATE" -> "redirect:/candidate/dashboard";
            case "EMPLOYER" -> "redirect:/employer/dashboard";
            case "ADMIN" -> "redirect:/admin/dashboard"; // In-memory admin will be handled in security
            default -> "redirect:/login?error";
        };
    }

    // === LOGOUT (handled by Spring Security) ===
    // POST /logout -> redirects to /login?logout

    // === API: Check if email exists (for frontend validation) ===
    @GetMapping("/api/check-email")
    @ResponseBody
    public ResponseEntity<?> checkEmail(@RequestParam String email) {
        boolean exists = userManager.existsByEmail(email);
        return ResponseEntity.ok().body("{\"exists\":" + exists + "}");
    }
}