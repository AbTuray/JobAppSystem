package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.service.JobManager;
import com.jobportal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private JobManager jobService;

    @GetMapping("/all")
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/{id}")
    public Job getJobById(@PathVariable Long id) {
        return jobService.getJobById(id);
    }
}
