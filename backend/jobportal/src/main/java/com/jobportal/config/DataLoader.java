package com.jobportal.config;

import com.jobportal.entity.User;
import com.jobportal.entity.UserType;
import com.jobportal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@jobportal.local").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@jobportal.local");
            admin.setPassword(passwordEncoder.encode("admin123")); // change in real usage
            admin.setUserType(UserType.ADMIN);
            admin.setFirstName("Built-in");
            admin.setLastName("Admin");
            userRepository.save(admin);
            System.out.println("Created built-in admin: admin@jobportal.local / admin123");
        }
    }
}
