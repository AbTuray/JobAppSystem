package com.jobportal.service;

import com.jobportal.entity.Employer;

import java.util.List;

public interface EmployerService {
    Employer createEmployer(Employer employer);
    Employer updateEmployer(Long id, Employer updated);
    void deleteEmployer(Long id);
    List<Employer> getAllEmployers();
    Employer findByUserId(Long userId);
}
