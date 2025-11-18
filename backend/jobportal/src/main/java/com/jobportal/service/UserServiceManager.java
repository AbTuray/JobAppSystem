package com.jobportal.service;

import com.jobportal.dto.RegisterEmployeeDto;
import com.jobportal.dto.RegisterEmployerDto;
import com.jobportal.entity.Employee;
import com.jobportal.entity.Employer;
import com.jobportal.entity.User;
import com.jobportal.entity.UserType;
import com.jobportal.repository.EmployeeRepository;
import com.jobportal.repository.EmployerRepository;
import com.jobportal.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceManager implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployerRepository employerRepository;

    @Override
    @Transactional
    public User registerEmployer(Object dto) {
        if (!(dto instanceof RegisterEmployerDto)) {
            throw new IllegalArgumentException("Expected RegisterEmployerDto");
        }
        RegisterEmployerDto r = (RegisterEmployerDto) dto;

        if (userRepository.findByEmail(r.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setEmail(r.getEmail());
        user.setFirstName(r.getFirstName());
        user.setLastName(r.getLastName());
        user.setUserType(UserType.EMPLOYER);
        user.setPassword(passwordEncoder.encode(r.getPassword()));

        User savedUser = userRepository.save(user);

        Employer employer = new Employer();
        employer.setUser(savedUser);
        employer.setPhone(r.getPhone());
        employer.setOffice(r.getOffice());
        employer.setAddress(r.getAddress());
        employerRepository.save(employer);

        return savedUser;
    }

    @Override
    @Transactional
    public User registerEmployee(Object dto) {
        if (!(dto instanceof RegisterEmployeeDto)) {
            throw new IllegalArgumentException("Expected RegisterEmployeeDto");
        }
        RegisterEmployeeDto r = (RegisterEmployeeDto) dto;

        if (userRepository.findByEmail(r.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setEmail(r.getEmail());
        user.setFirstName(r.getFirstName());
        user.setLastName(r.getLastName());
        user.setUserType(UserType.EMPLOYEE);
        user.setPassword(passwordEncoder.encode(r.getPassword()));

        User savedUser = userRepository.save(user);

        Employee employee = new Employee();
        employee.setUser(savedUser);
        employee.setPhone(r.getPhone());
        employee.setAddress(r.getAddress());
        employeeRepository.save(employee);

        return savedUser;
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> u = userRepository.findByEmail(email);
        return u.orElse(null);
    }

}
