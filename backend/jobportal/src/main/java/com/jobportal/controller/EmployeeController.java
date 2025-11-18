package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.entity.JobApplication;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private JobService jobService;
    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    public ResponseEntity<List<Job>> searchJobs(@RequestParam(required = false) String q) {
        if (q == null || q.isBlank()) return ResponseEntity.ok(jobService.getAllJobs());
        return ResponseEntity.ok(jobService.searchJobs(q));
    }

    @RequestMapping(value = "/apply", method = RequestMethod.POST)
    public ResponseEntity<?> applyForJob(@Valid @RequestBody JobApplication application, Principal principal) {
        // set employee based on principal before saving
        JobApplication created = applicationService.applyForJob(application);
        return ResponseEntity.ok(created);
    }

    @RequestMapping(value = "/application/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editApplication(@PathVariable Long id, @RequestBody JobApplication updated) {
        JobApplication edited = applicationService.editApplication(id, updated);
        return ResponseEntity.ok(edited);
    }

    @RequestMapping(value = "/application/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok("Deleted");
    }
}
