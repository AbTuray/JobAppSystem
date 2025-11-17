package com.jobportal.service;

import com.jobportal.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminManager implements AdminService {

    private final String ADMIN_USERNAME = "admin";
    private final String ADMIN_PASSWORD = "admin123";

    @Override
    public boolean login(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
}
