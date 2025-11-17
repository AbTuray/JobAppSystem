package com.jobportal.service;

import com.jobportal.entity.Application;
import com.jobportal.entity.Candidate;
import com.jobportal.entity.Job;
import com.jobportal.enums.ApplicationStatus;

import java.util.List;

public interface ApplicationService {

    Application applyForJob(Candidate candidate, Job job);

    List<Application> getApplicationsByCandidate(Candidate candidate);

    List<Application> getApplicationsByJob(Job job);

    List<Application> getApplicationsByStatus(ApplicationStatus applicationStatus);

    Application updateApplicationStatus(Long applicationId, String status);

    void deleteApplication(Long applicationId);
}
