package com.jobportal.service;

import com.jobportal.entity.JobApplication;

import java.util.List;

public interface ApplicationService {
    JobApplication applyForJob(JobApplication application);
    JobApplication editApplication(Long id, JobApplication updated);
    void deleteApplication(Long id);
    List<JobApplication> getApplicationsForJob(Long jobId);
    List<JobApplication> getApplicationsForEmployee(Long employeeId);
    List<JobApplication> getAllApplications();
    JobApplication approveByAdmin(Long applicationId);
    JobApplication acceptByEmployer(Long applicationId);
}
