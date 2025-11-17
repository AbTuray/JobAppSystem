package com.jobportal.service;

import com.jobportal.dto.LoginDto;
import com.jobportal.entity.User;

public interface UserService {
    User registerEmployer(/*RegisterEmployerDto dto*/ Object dto);
    User registerEmployee(/*RegisterEmployeeDto dto*/ Object dto);
    User findByEmail(String email);
}
