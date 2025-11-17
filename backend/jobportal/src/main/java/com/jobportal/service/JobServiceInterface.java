// src/main/java/com/jobportal/service/JobServiceInterface.java
package com.jobportal.service;

import com.jobportal.dto.JobDTO;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;

import java.util.List;

public interface JobServiceInterface {

    // Create new job
    Job createJob(JobDTO jobDTO, User employer);

    // Update job
    Job updateJob(Long jobId, JobDTO jobDTO, User employer);

    // Delete job
    void deleteJob(Long jobId, User employer);

    // Get job by ID
    JobDTO getJobById(Long jobId);

    // Get all jobs by employer
    List<JobDTO> getJobsByEmployer(User employer);

    // Get all active jobs (public)
    List<JobDTO> getActiveJobs();

    // Search active jobs by keyword
    List<JobDTO> searchJobs(String keyword);

    // Deactivate job
    void deactivateJob(Long jobId, User employer);
}