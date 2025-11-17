package com.jobportal.service;

import com.jobportal.dto.CandidateRegistrationDTO;
import com.jobportal.dto.CandidateLoginDTO;
import com.jobportal.entity.Candidate;
import com.jobportal.repository.CandidateRepository;
import com.jobportal.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CandidateManager implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public Candidate registerCandidate(CandidateRegistrationDTO dto) {
        if(candidateRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already registered");
        }
        Candidate candidate = new Candidate();
        candidate.setFirstName(dto.getFirstName());
        candidate.setLastName(dto.getLastName());
        candidate.setEmail(dto.getEmail());
        candidate.setPhone(dto.getPhone());
        candidate.setAddress(dto.getAddress());
        candidate.setPassword(passwordEncoder.encode(dto.getPassword()));
        return candidateRepository.save(candidate);
    }

    @Override
    public Candidate loginCandidate(CandidateLoginDTO dto) {
        Candidate candidate = candidateRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
        if(!passwordEncoder.matches(dto.getPassword(), candidate.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return candidate;
    }

    @Override
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
    }

    @Override
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }

    public Candidate login(String email, String password) {
        return candidateRepository.findByEmailAndPassword(email, password).orElse(null);
    }

    public Candidate findByEmail(String email) {
        return candidateRepository.findByEmail(email).orElse(null);
    }
}
