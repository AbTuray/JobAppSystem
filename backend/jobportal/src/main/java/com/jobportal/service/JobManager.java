// src/main/java/com/jobportal/service/JobManager.java
package com.jobportal.service;

import com.jobportal.dto.JobDTO;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobManager implements JobServiceInterface {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Job createJob(JobDTO jobDTO, User employer) {
        // Validate employer
        if (!"EMPLOYER".equals(employer.getUserType())) {
            throw new RuntimeException("Only employers can create jobs");
        }

        Job job = new Job();
        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setRequirements(jobDTO.getRequirements());
        job.setLocation(jobDTO.getLocation());
        job.setSalaryRange(jobDTO.getSalaryRange());
        job.setPostedDate(LocalDate.now());
        job.setActive(true);
        job.setEmployer(employer);

        return jobRepository.save(job);
    }

    @Override
    public Job updateJob(Long jobId, JobDTO jobDTO, User employer) {
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        if (jobOpt.isEmpty()) {
            throw new RuntimeException("Job not found");
        }

        Job job = jobOpt.get();
        // Ownership check
        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("You can only edit your own jobs");
        }

        job.setTitle(jobDTO.getTitle());
        job.setDescription(jobDTO.getDescription());
        job.setRequirements(jobDTO.getRequirements());
        job.setLocation(jobDTO.getLocation());
        job.setSalaryRange(jobDTO.getSalaryRange());

        return jobRepository.save(job);
    }

    @Override
    public void deleteJob(Long jobId, User employer) {
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        if (jobOpt.isEmpty()) {
            throw new RuntimeException("Job not found");
        }

        Job job = jobOpt.get();
        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("You can only delete your own jobs");
        }

        jobRepository.delete(job);
    }

    @Override
    public JobDTO getJobById(Long jobId) {
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        if (jobOpt.isEmpty()) {
            return null;
        }
        return mapToJobDTO(jobOpt.get());
    }

    @Override
    public List<JobDTO> getJobsByEmployer(User employer) {
        return jobRepository.findByEmployer(employer)
                .stream()
                .map(this::mapToJobDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDTO> getActiveJobs() {
        return jobRepository.findByActiveTrue()
                .stream()
                .map(this::mapToJobDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<JobDTO> searchJobs(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getActiveJobs();
        }
        return jobRepository.searchActiveJobs(keyword.trim())
                .stream()
                .map(this::mapToJobDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deactivateJob(Long jobId, User employer) {
        Optional<Job> jobOpt = jobRepository.findById(jobId);
        if (jobOpt.isEmpty()) {
            throw new RuntimeException("Job not found");
        }

        Job job = jobOpt.get();
        if (!job.getEmployer().getId().equals(employer.getId())) {
            throw new RuntimeException("You can only deactivate your own jobs");
        }

        job.setActive(false);
        jobRepository.save(job);
    }

    // Helper: Entity -> DTO
    private JobDTO mapToJobDTO(Job job) {
        JobDTO dto = new JobDTO();
        dto.setId(job.getId());
        dto.setTitle(job.getTitle());
        dto.setDescription(job.getDescription());
        dto.setRequirements(job.getRequirements());
        dto.setLocation(job.getLocation());
        dto.setSalaryRange(job.getSalaryRange());
        dto.setPostedDate(job.getPostedDate());
        dto.setActive(job.isActive());
        dto.setEmployerId(job.getEmployer().getId());

        // Format: "John Doe - ABC Corp"
        String employerName = job.getEmployer().getFirstName() + " " +
                job.getEmployer().getLastName();
        if (job.getEmployer().getOffice() != null && !job.getEmployer().getOffice().trim().isEmpty()) {
            employerName += " - " + job.getEmployer().getOffice();
        }
        dto.setEmployerName(employerName);

        return dto;
    }
}