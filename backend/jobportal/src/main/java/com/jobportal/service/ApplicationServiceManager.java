package com.jobportal.service;

import com.jobportal.entity.Employee;
import com.jobportal.entity.Job;
import com.jobportal.entity.JobApplication;
import com.jobportal.repository.EmployeeRepository;
import com.jobportal.repository.JobApplicationRepository;
import com.jobportal.repository.JobRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationServiceManager implements ApplicationService{

    @Autowired
    private JobApplicationRepository applicationRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public JobApplication applyForJob(JobApplication application) {
        // Validate job and employee exist
        if (application.getJob() == null || application.getJob().getId() == null) {
            throw new RuntimeException("Job must be specified");
        }
        if (application.getEmployee() == null || application.getEmployee().getId() == null) {
            throw new RuntimeException("Employee must be specified");
        }

        Job job = jobRepository.findById(application.getJob().getId())
                .orElseThrow(() -> new RuntimeException("Job not found"));
        Employee emp = employeeRepository.findById(application.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Prevent duplicate application by same employee for same job (simple check)
        List<JobApplication> existing = applicationRepository.findByJobId(job.getId());
        boolean already = existing.stream()
                .anyMatch(a -> a.getEmployee() != null && a.getEmployee().getId().equals(emp.getId()));
        if (already) {
            throw new RuntimeException("You have already applied for this job");
        }

        application.setJob(job);
        application.setEmployee(emp);
        application.setApprovedByAdmin(false);
        application.setAcceptedByEmployer(false);
        return applicationRepository.save(application);
    }

    @Override
    @Transactional
    public JobApplication editApplication(Long id, JobApplication updated) {
        JobApplication existing = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        // Only allow editing of mutable fields: cvLink (for example)
        existing.setCvLink(updated.getCvLink());
        // reset approval if you want — here we keep approval state unchanged
        return applicationRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteApplication(Long id) {
        if (!applicationRepository.existsById(id)) {
            throw new RuntimeException("Application not found");
        }
        applicationRepository.deleteById(id);
    }

    @Override
    public List<JobApplication> getApplicationsForJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }

    @Override
    public List<JobApplication> getApplicationsForEmployee(Long employeeId) {
        return applicationRepository.findByEmployeeId(employeeId);
    }

    @Override
    public List<JobApplication> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    @Transactional
    public JobApplication approveByAdmin(Long applicationId) {
        JobApplication existing = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        existing.setApprovedByAdmin(true);
        return applicationRepository.save(existing);
    }

    @Override
    @Transactional
    public JobApplication acceptByEmployer(Long applicationId) {
        JobApplication existing = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        if (!existing.isApprovedByAdmin()) {
            throw new RuntimeException("Application must be approved by admin before employer can accept");
        }
        existing.setAcceptedByEmployer(true);
        return applicationRepository.save(existing);
    }

}
