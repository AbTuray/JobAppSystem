package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.entity.Employer;
import com.jobportal.service.EmployerService;
import com.jobportal.service.JobService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/employer")
public class EmployerController {

    @Autowired
    private EmployerService employerService;
    @Autowired
    private JobService jobService;

    @RequestMapping(value = "/job", method = RequestMethod.POST)
    public ResponseEntity<?> addJob(@Valid @RequestBody Job job, Principal principal) {
        // find employer by principal and set job.employer
        Job created = jobService.createJob(job);
        return ResponseEntity.ok(created);
    }

    @RequestMapping(value = "/job/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editJob(@PathVariable Long id, @RequestBody Job job) {
        Job updated = jobService.updateJob(id, job);
        return ResponseEntity.ok(updated);
    }

    @RequestMapping(value = "/job/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.ok("Deleted");
    }

    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    public ResponseEntity<List<Job>> myJobs(Principal principal) {
        // find employer by principal and return jobs by employer
        return ResponseEntity.ok(jobService.getAllJobs());
    }
}
