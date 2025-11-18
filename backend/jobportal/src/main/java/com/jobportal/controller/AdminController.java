package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.entity.JobApplication;
import com.jobportal.entity.Employer;
import com.jobportal.entity.Employee;
import com.jobportal.service.ApplicationService;
import com.jobportal.service.EmployerService;
import com.jobportal.service.EmployeeService;
import com.jobportal.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private JobService jobService;
    @Autowired
    private EmployerService employerService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ApplicationService applicationService;


    @RequestMapping(value = "/jobs", method = RequestMethod.GET)
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }


    @RequestMapping(value = "/employers", method = RequestMethod.GET)
    public ResponseEntity<List<Employer>> getAllEmployers() {
        return ResponseEntity.ok(employerService.getAllEmployers());
    }


    @RequestMapping(value = "/employees", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }


    @RequestMapping(value = "/applications", method = RequestMethod.GET)
    public ResponseEntity<List<JobApplication>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }


    @RequestMapping(value = "/applications/{id}/approve", method = RequestMethod.POST)
    public ResponseEntity<?> approveApplication(@PathVariable Long id) {
        JobApplication app = applicationService.approveByAdmin(id);
        return ResponseEntity.ok(app);
    }


    @RequestMapping(value = "/employer/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployer(@PathVariable Long id) {
        employerService.deleteEmployer(id);
        return ResponseEntity.ok("Employer deleted");
    }


    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted");
    }
}
