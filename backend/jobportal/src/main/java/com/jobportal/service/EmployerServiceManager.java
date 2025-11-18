package com.jobportal.service;

import com.jobportal.entity.Employer;
import com.jobportal.entity.User;
import com.jobportal.repository.EmployerRepository;
import com.jobportal.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployerServiceManager implements EmployerService {

    @Autowired
    private EmployerRepository employerRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Employer createEmployer(Employer employer) {
        // ensure user exists and is EMPLOYER (optional check)
        User u = employer.getUser();
        if (u == null) {
            throw new RuntimeException("Employer must reference a User");
        }
        return employerRepository.save(employer);
    }

    @Override
    @Transactional
    public Employer updateEmployer(Long id, Employer updated) {
        Employer existing = employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employer not found"));
        // update allowed fields
        existing.setAddress(updated.getAddress());
        existing.setOffice(updated.getOffice());
        existing.setPhone(updated.getPhone());
        return employerRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteEmployer(Long id) {
        if (!employerRepository.existsById(id)) {
            throw new RuntimeException("Employer not found");
        }
        employerRepository.deleteById(id);
    }

    @Override
    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    @Override
    public Employer findByUserId(Long userId) {
        // simple linear search via repository: we assume Employer has user relation
        Optional<Employer> maybe = employerRepository.findAll()
                .stream()
                .filter(e -> e.getUser() != null && e.getUser().getId().equals(userId))
                .findFirst();
        return maybe.orElse(null);
    }

}
