package com.jobportal.service;

import com.jobportal.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee createEmployee(Employee e);
    Employee updateEmployee(Long id, Employee updated);
    void deleteEmployee(Long id);
    List<Employee> getAllEmployees();
    Employee findByUserId(Long userId);
}
