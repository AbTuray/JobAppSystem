// src/main/java/com/jobportal/repository/JobRepository.java
package com.jobportal.repository;

import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {

    // Find all jobs posted by a specific employer
    List<Job> findByEmployer(User employer);

    // Find all active jobs (for public search)
    List<Job> findByActiveTrue();

    // Find active jobs with title or description containing keyword (case-insensitive)
    @Query("SELECT j FROM Job j WHERE j.active = true AND " +
            "(LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Job> searchActiveJobs(@Param("keyword") String keyword);
}