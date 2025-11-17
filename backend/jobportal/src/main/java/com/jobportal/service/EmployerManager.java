package com.jobportal.service;

import com.jobportal.dto.EmployerRegistrationDTO;
import com.jobportal.dto.EmployerLoginDTO;
import com.jobportal.entity.Employer;
import com.jobportal.repository.EmployerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployerManager implements EmployerService {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Employer registerEmployer(EmployerRegistrationDTO dto) {
        if(employerRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        Employer employer = new Employer();
        employer.setFirstName(dto.getFirstName());
        employer.setLastName(dto.getLastName());
        employer.setEmail(dto.getEmail());
        employer.setPhone(dto.getPhone());
        employer.setOffice(dto.getOffice());
        employer.setAddress(dto.getAddress());
        employer.setPassword(passwordEncoder.encode(dto.getPassword()));
        return employerRepository.save(employer);
    }

    @Override
    public Employer loginEmployer(EmployerLoginDTO dto) {
        Employer employer = employerRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if(!passwordEncoder.matches(dto.getPassword(), employer.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return employer;
    }

    @Override
    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    @Override
    public Employer getEmployerById(Long id) {
        return employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employer not found"));
    }

    @Override
    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }

    public Employer login(String email, String password) {
        return employerRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    public Employer findByEmail(String email) {
        return employerRepository.findByEmail(email).orElse(null);
    }

    public Employer getEmployerByEmail(String username) {
        return employerRepository.findByEmail(username).orElse(null);
    }
}
