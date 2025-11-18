package com.jobportal.service;

import com.jobportal.entity.Employee;
import com.jobportal.repository.EmployeeRepository;
import com.jobportal.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceManager implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public Employee createEmployee(Employee e) {
        if (e.getUser() == null) {
            throw new RuntimeException("Employee must reference a User");
        }
        return employeeRepository.save(e);
    }

    @Override
    @Transactional
    public Employee updateEmployee(Long id, Employee updated) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        existing.setAddress(updated.getAddress());
        existing.setPhone(updated.getPhone());
        return employeeRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException("Employee not found");
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findByUserId(Long userId) {
        Optional<Employee> maybe = employeeRepository.findAll()
                .stream()
                .filter(e -> e.getUser() != null && e.getUser().getId().equals(userId))
                .findFirst();
        return maybe.orElse(null);
    }
}
