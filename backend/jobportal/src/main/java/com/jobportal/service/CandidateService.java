package com.jobportal.service;

import com.jobportal.dto.CandidateRegistrationDTO;
import com.jobportal.dto.CandidateLoginDTO;
import com.jobportal.entity.Candidate;
import java.util.List;

public interface CandidateService {

    Candidate registerCandidate(CandidateRegistrationDTO dto);

    Candidate loginCandidate(CandidateLoginDTO dto);

    List<Candidate> getAllCandidates();

    Candidate getCandidateById(Long id);

    void deleteCandidate(Long id);
}
