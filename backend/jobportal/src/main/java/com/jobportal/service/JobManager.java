package com.jobportal.service;

import com.jobportal.entity.Job;
import com.jobportal.entity.Employer;
import com.jobportal.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobManager implements JobService {

    @Autowired
    private JobRepository jobRepository;

    @Override
    public Job addJob(Job job) {
        if (job.getEmployer() == null || job.getEmployer().getId() == null) {
            throw new RuntimeException("Employer must not be null");
        }
        return jobRepository.save(job);
//        return jobRepository.save(job);
    }

    @Override
    public Job updateJob(Job job) {
        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(Long jobId) {
        jobRepository.deleteById(jobId);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public List<Job> getJobsByEmployer(Employer employer) {
        return jobRepository.findByEmployer(employer);
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }

    public void deleteJobByEmployer(Long id, Employer employer) {
        jobRepository.deleteById(id);
    }
}
