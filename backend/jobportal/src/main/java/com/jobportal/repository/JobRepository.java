package com.jobportal.repository;

import com.jobportal.entity.Job;
import com.jobportal.entity.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    List<Job> findByEmployer(Employer employer);

    List<Job> findByTitleContainingIgnoreCase(String keyword); // For search by candidate
}
