// src/main/java/com/jobportal/repository/ApplicationRepository.java
package com.jobportal.repository;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    // Find all applications for a job
    List<Application> findByJob(Job job);

    // Find all applications by candidate
    List<Application> findByCandidate(User candidate);

    // Find applications by status
    List<Application> findByStatus(String status);

    // Find PENDING applications (for Admin approval)
    List<Application> findByStatusOrderByAppliedDateDesc(String status);

    // Find APPROVED applications for a job (for Employer to view)
    @Query("SELECT a FROM Application a WHERE a.job = :job AND a.status = 'APPROVED'")
    List<Application> findApprovedByJob(@Param("job") Job job);

    // Find all applications for jobs posted by a specific employer (after approval)
    @Query("SELECT a FROM Application a JOIN a.job j WHERE j.employer = :employer AND a.status IN ('APPROVED', 'ACCEPTED')")
    List<Application> findApprovedApplicationsByEmployer(@Param("employer") User employer);

    // Check if candidate already applied to a job
    boolean existsByCandidateAndJob(User candidate, Job job);
}