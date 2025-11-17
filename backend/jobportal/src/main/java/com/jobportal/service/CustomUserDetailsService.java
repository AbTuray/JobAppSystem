package com.jobportal.service;


import com.jobportal.entity.Candidate;
import com.jobportal.entity.Employer;
import com.jobportal.repository.CandidateRepository;
import com.jobportal.repository.EmployerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployerRepository employerRepository;
    private final CandidateRepository candidateRepository;

    public CustomUserDetailsService(EmployerRepository employerRepository, CandidateRepository candidateRepository) {
        this.employerRepository = employerRepository;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // First check employer
        Employer employer = employerRepository.findByEmail(email).orElse(null);
        if(employer != null){
            return User.builder()
                    .username(employer.getEmail())
                    .password(employer.getPassword())
                    .roles("EMPLOYER")
                    .build();
        }

        // Then check candidate
        Candidate candidate = candidateRepository.findByEmail(email).orElse(null);
        if(candidate != null){
            return User.builder()
                    .username(candidate.getEmail())
                    .password(candidate.getPassword())
                    .roles("CANDIDATE")
                    .build();
        }

        // If neither found, throw exception
        throw new UsernameNotFoundException("User not found");
    }
}

