package com.jobportal.repository;

import com.jobportal.entity.Application;

import com.jobportal.entity.Candidate;
import com.jobportal.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.jobportal.enums.ApplicationStatus;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByCandidateId(Long candidateId);

    List<Application> findByJobId(Long jobId);

    @Query("SELECT a FROM Application a WHERE a.status = :status")
    List<Application> findByStatus(ApplicationStatus status);

    // Employers see only admin-approved applications
    @Query("SELECT a FROM Application a WHERE a.job.employer.id = :employerId AND a.status = 'APPROVED_BY_ADMIN'")
    List<Application> findApprovedForEmployer(Long employerId);

    List<Application> findByCandidate(Candidate candidate);

    List<Application> findByJob(Job job);
}
