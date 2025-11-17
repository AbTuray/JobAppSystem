// src/main/java/com/jobportal/service/ApplicationManager.java
package com.jobportal.service;

import com.jobportal.dto.ApplicationDTO;
import com.jobportal.dto.ApplicationResponseDTO;
import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApplicationManager implements ApplicationServiceInterface {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Application applyToJob(ApplicationDTO applicationDTO, User candidate) {
        // Validate candidate
        if (!"CANDIDATE".equals(candidate.getUserType())) {
            throw new RuntimeException("Only candidates can apply for jobs");
        }

        Optional<Job> jobOpt = jobRepository.findById(applicationDTO.getJobId());
        if (jobOpt.isEmpty()) {
            throw new RuntimeException("Job not found");
        }

        Job job = jobOpt.get();
        if (!job.isActive()) {
            throw new RuntimeException("This job is no longer active");
        }

        // Prevent duplicate application
        if (applicationRepository.existsByCandidateAndJob(candidate, job)) {
            throw new RuntimeException("You have already applied for this job");
        }

        Application application = new Application();
        application.setAppliedDate(LocalDate.now());
        application.setCoverLetter(applicationDTO.getCoverLetter());
        application.setStatus("PENDING");
        application.setCandidate(candidate);
        application.setJob(job);

        return applicationRepository.save(application);
    }

    @Override
    public boolean hasApplied(User candidate, Job job) {
        return applicationRepository.existsByCandidateAndJob(candidate, job);
    }

    @Override
    public List<ApplicationResponseDTO> getApplicationsByCandidate(User candidate) {
        return applicationRepository.findByCandidate(candidate)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationResponseDTO> getPendingApplications() {
        return applicationRepository.findByStatusOrderByAppliedDateDesc("PENDING")
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void approveApplication(Long applicationId) {
        Application app = getApplicationOrThrow(applicationId);
        if (!"PENDING".equals(app.getStatus())) {
            throw new RuntimeException("Only PENDING applications can be approved");
        }
        app.setStatus("APPROVED");
        applicationRepository.save(app);
    }

    @Override
    public void rejectApplication(Long applicationId) {
        Application app = getApplicationOrThrow(applicationId);
        if (!"PENDING".equals(app.getStatus())) {
            throw new RuntimeException("Only PENDING applications can be rejected");
        }
        app.setStatus("REJECTED");
        applicationRepository.save(app);
    }

    @Override
    public List<ApplicationResponseDTO> getApprovedApplicationsByJob(Job job) {
        return applicationRepository.findApprovedByJob(job)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationResponseDTO> getApprovedApplicationsByEmployer(User employer) {
        return applicationRepository.findApprovedApplicationsByEmployer(employer)
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void acceptApplicant(Long applicationId, User employer) {
        Application app = getApplicationOrThrow(applicationId);
        Job job = app.getJob();

        // Ownership check
        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("You can only accept applicants for your own jobs");
        }

        if (!"APPROVED".equals(app.getStatus())) {
            throw new RuntimeException("Only APPROVED applications can be accepted");
        }

        app.setStatus("ACCEPTED");
        applicationRepository.save(app);
    }

    @Override
    public void removeApplicant(Long applicationId, User employer) {
        Application app = getApplicationOrThrow(applicationId);
        Job job = app.getJob();

        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("You can only remove applicants from your own jobs");
        }

        if (!List.of("APPROVED", "ACCEPTED").contains(app.getStatus())) {
            throw new RuntimeException("Only APPROVED or ACCEPTED applications can be removed");
        }

        app.setStatus("REMOVED");
        applicationRepository.save(app);
    }

    @Override
    public Application updateApplication(Long applicationId, String coverLetter, User candidate) {
        Application app = getApplicationOrThrow(applicationId);

        if (!app.getCandidate().getId().equals(candidate.getId())) {
            throw new RuntimeException("You can only edit your own applications");
        }

        if (!"PENDING".equals(app.getStatus())) {
            throw new RuntimeException("Only PENDING applications can be edited");
        }

        app.setCoverLetter(coverLetter);
        return applicationRepository.save(app);
    }

    @Override
    public void deleteApplication(Long applicationId, User candidate) {
        Application app = getApplicationOrThrow(applicationId);

        if (!app.getCandidate().getId().equals(candidate.getId())) {
            throw new RuntimeException("You can only delete your own applications");
        }

        if (!"PENDING".equals(app.getStatus())) {
            throw new RuntimeException("Only PENDING applications can be deleted");
        }

        applicationRepository.delete(app);
    }

    // Helper: Find application or throw
    private Application getApplicationOrThrow(Long id) {
        Optional<Application> appOpt = applicationRepository.findById(id);
        if (appOpt.isEmpty()) {
            throw new RuntimeException("Application not found");
        }
        return appOpt.get();
    }

    // Helper: Entity -> Response DTO
    private ApplicationResponseDTO mapToResponseDTO(Application app) {
        ApplicationResponseDTO dto = new ApplicationResponseDTO();
        dto.setId(app.getId());
        dto.setAppliedDate(app.getAppliedDate());
        dto.setCoverLetter(app.getCoverLetter());
        dto.setStatus(app.getStatus());

        // Candidate Info
        User candidate = app.getCandidate();
        dto.setCandidateId(candidate.getId());
        dto.setCandidateName(candidate.getFirstName() + " " + candidate.getLastName());
        dto.setCandidateEmail(candidate.getEmail());
        dto.setCandidatePhone(candidate.getPhone());
        dto.setCandidateAddress(candidate.getAddress());

        // Job Info
        Job job = app.getJob();
        dto.setJobId(job.getId());
        dto.setJobTitle(job.getTitle());

        return dto;
    }
}