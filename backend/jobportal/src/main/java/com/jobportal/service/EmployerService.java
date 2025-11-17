package com.jobportal.service;

import com.jobportal.dto.EmployerRegistrationDTO;
import com.jobportal.dto.EmployerLoginDTO;
import com.jobportal.entity.Employer;
import java.util.List;

public interface EmployerService {

    Employer registerEmployer(EmployerRegistrationDTO dto);

    Employer loginEmployer(EmployerLoginDTO dto);

    List<Employer> getAllEmployers();

    Employer getEmployerById(Long id);

    void deleteEmployer(Long id);
}
