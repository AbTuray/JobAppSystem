package com.jobportal.service;

import com.jobportal.entity.Application;
import com.jobportal.entity.Candidate;
import com.jobportal.entity.Job;
import com.jobportal.enums.ApplicationStatus;
import com.jobportal.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ApplicationManager implements ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Override
    public Application applyForJob(Candidate candidate, Job job) {
        Application app = new Application();
        app.setCandidate(candidate);
        app.setJob(job);
        app.setStatus("Pending");
        return applicationRepository.save(app);
    }

    @Override
    public List<Application> getApplicationsByCandidate(Candidate candidate) {
        return applicationRepository.findByCandidate(candidate);
    }

    @Override
    public List<Application> getApplicationsByJob(Job job) {
        return applicationRepository.findByJob(job);
    }

    @Override
    public List<Application> getApplicationsByStatus(ApplicationStatus applicationStatus) {
        return applicationRepository.findByStatus(applicationStatus);
    }

    @Override
    public Application updateApplicationStatus(Long applicationId, String status) {
        Application app = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        app.setStatus(status);
        return applicationRepository.save(app);
    }

    @Override
    public void deleteApplication(Long applicationId) {
        applicationRepository.deleteById(applicationId);
    }
}
