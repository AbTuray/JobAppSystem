package com.jobportal.service;

import com.jobportal.entity.Employer;
import com.jobportal.entity.Job;
import com.jobportal.repository.EmployeeRepository;
import com.jobportal.repository.EmployerRepository;
import com.jobportal.repository.JobRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServiceManager implements JobService{

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private EmployerRepository employerRepository;

    @Override
    @Transactional
    public Job createJob(Job job) {
        // Ensure employer is present and persisted
        Employer emp = job.getEmployer();
        if (emp == null || emp.getId() == null) {
            throw new RuntimeException("Job must be associated with a valid employer");
        }
        // Optionally check employer exists
        employerRepository.findById(emp.getId())
                .orElseThrow(() -> new RuntimeException("Employer not found for this job"));
        return jobRepository.save(job);
    }

    @Override
    @Transactional
    public Job updateJob(Long id, Job updated) {
        Job existing = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setLocation(updated.getLocation());
        // do not change employer here (unless you want to allow transfer)
        return jobRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteJob(Long id) {
        if (!jobRepository.existsById(id)) {
            throw new RuntimeException("Job not found");
        }
        jobRepository.deleteById(id);
    }

    @Override
    public List<Job> searchJobs(String query) {
        if (query == null || query.isBlank()) {
            return jobRepository.findAll();
        }
        return jobRepository.findByTitleContainingIgnoreCase(query);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public List<Job> getJobsByEmployer(Long employerId) {
        return jobRepository.findByEmployerId(employerId);
    }

    @Override
    public Job findById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }
}
