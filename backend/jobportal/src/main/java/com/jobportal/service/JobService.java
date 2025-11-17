package com.jobportal.service;

import com.jobportal.entity.Job;

import java.util.List;

public interface JobService {
    Job createJob(Job job);
    Job updateJob(Long id, Job updated);
    void deleteJob(Long id);
    List<Job> searchJobs(String query);
    List<Job> getAllJobs();
    List<Job> getJobsByEmployer(Long employerId);
    Job findById(Long id);
}
