package com.jobportal.service;

import com.jobportal.entity.Job;
import com.jobportal.entity.Employer;
import java.util.List;

public interface JobService {

    Job addJob(Job job);

    Job updateJob(Job job);

    void deleteJob(Long jobId);

    List<Job> getAllJobs();

    List<Job> getJobsByEmployer(Employer employer);

    Job getJobById(Long id);
}
