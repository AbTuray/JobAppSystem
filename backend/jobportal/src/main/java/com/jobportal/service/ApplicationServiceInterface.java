// src/main/java/com/jobportal/service/ApplicationServiceInterface.java
package com.jobportal.service;

import com.jobportal.dto.ApplicationDTO;
import com.jobportal.dto.ApplicationResponseDTO;
import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;

import java.util.List;

public interface ApplicationServiceInterface {

    // Candidate applies to job
    Application applyToJob(ApplicationDTO applicationDTO, User candidate);

    // Check if already applied
    boolean hasApplied(User candidate, Job job);

    // Get candidate's applications
    List<ApplicationResponseDTO> getApplicationsByCandidate(User candidate);

    // Get PENDING applications (Admin)
    List<ApplicationResponseDTO> getPendingApplications();

    // Approve application (Admin)
    void approveApplication(Long applicationId);

    // Reject application (Admin)
    void rejectApplication(Long applicationId);

    // Get approved applications for a job (Employer)
    List<ApplicationResponseDTO> getApprovedApplicationsByJob(Job job);

    // Get all approved/accepted applications for employer
    List<ApplicationResponseDTO> getApprovedApplicationsByEmployer(User employer);

    // Employer accepts applicant
    void acceptApplicant(Long applicationId, User employer);

    // Employer removes applicant
    void removeApplicant(Long applicationId, User employer);

    // Edit application (Candidate)
    Application updateApplication(Long applicationId, String coverLetter, User candidate);

    // Delete application (Candidate)
    void deleteApplication(Long applicationId, User candidate);
}