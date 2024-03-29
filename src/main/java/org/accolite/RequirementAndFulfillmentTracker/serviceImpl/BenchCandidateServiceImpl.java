package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import jakarta.persistence.EntityNotFoundException;

import jakarta.transaction.Transactional;
import org.accolite.RequirementAndFulfillmentTracker.config.JWTService;
import org.accolite.RequirementAndFulfillmentTracker.entity.*;
import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.exception.UserUnauthorisedException;
import org.accolite.RequirementAndFulfillmentTracker.model.AccountDTO;
import org.accolite.RequirementAndFulfillmentTracker.model.BenchCandidateDTO;
import org.accolite.RequirementAndFulfillmentTracker.model.RequirementDTO;
import org.accolite.RequirementAndFulfillmentTracker.model.UserRoleDTO;
import org.accolite.RequirementAndFulfillmentTracker.repository.BenchCandidateRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.BenchCandidateService;
import org.accolite.RequirementAndFulfillmentTracker.utils.EntityToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BenchCandidateServiceImpl implements BenchCandidateService {

    @Autowired
    private BenchCandidateRepository benchCandidateRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private JWTService jwtService;
    @Autowired
    private EntityToDTO entityToDTO;
    List<Role> authorised_roles = new ArrayList<>(List.of(Role.REQUIREMENT_MANAGER, Role.BENCH_MANAGER, Role.ADMIN, Role.SUPER_ADMIN));
    @Override
    public ResponseEntity<BenchCandidateDTO> addCandidate(BenchCandidateDTO candidate) {
            checkIfAuthorized();
        System.out.println(candidate);
        // check if the candidate exists to avoid duplicate entries
//        BenchCandidate existingCandidate = benchCandidateRepository.findById(candidate.getId()).orElse(null);
//        if(existingCandidate == null) {

            UserRole benchManager = userRoleRepository.findById(candidate.getBenchManager().getId()).orElseThrow(() -> new ResourceNotFoundException("Bench manager not found exception"));
            BenchCandidate newCandidate = BenchCandidate.builder()
                    .candidateName(candidate.getCandidateName())
                    .benchManager(benchManager)
                    .startDate(candidate.getStartDate())
                    .endDate(candidate.getEndDate())
                    .candidateStatus(candidate.getCandidateStatus())
                    .benchCandidateSkills(candidate.getBenchCandidateSkills())
                    .build();

            newCandidate = benchCandidateRepository.save(newCandidate);

            return ResponseEntity.ok(entityToDTO.getBenchCandidateDTO(newCandidate));
//        }
//
//        String errorMessage = "User with username " + entityToDTO.getBenchCandidateDTO(existingCandidate).getCandidateName() + " already exists.";
//        return ResponseEntity.
    }

    @Override
    public List<BenchCandidateDTO> getAllCandidates() {
        List<BenchCandidate> benchCandidatesList = benchCandidateRepository.findAll().stream().filter(candidate -> {
            return candidate.getCandidateStatus() != CandidateStatus.SELECTED;
        }).toList();

        return benchCandidatesList.stream().map(candidate -> entityToDTO.getBenchCandidateDTO(candidate)).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResponseEntity<BenchCandidateDTO> updateCandidate(Long id, BenchCandidateDTO updatedCandidate) {
        checkIfAuthorized();
        BenchCandidate existingCandidate = benchCandidateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bench Candidate not found"));

        UserRole benchManager = userRoleRepository.findById(updatedCandidate.getBenchManager().getId()).orElseThrow(() -> new ResourceNotFoundException("Bench Manager not found"));

        existingCandidate.setCandidateName(updatedCandidate.getCandidateName());
        existingCandidate.setCandidateStatus(updatedCandidate.getCandidateStatus());
//        existingCandidate.setSkills(updatedCandidate.getSkills());
        existingCandidate.setStartDate(updatedCandidate.getStartDate());
        existingCandidate.setEndDate(updatedCandidate.getEndDate());
        existingCandidate.setBenchManager(benchManager);

        existingCandidate = benchCandidateRepository.save(existingCandidate);

        BenchCandidateDTO benchCandidateDTO = entityToDTO.getBenchCandidateDTO(existingCandidate);
        return ResponseEntity.ok(benchCandidateDTO);
    }

//    @Override
//    public ResponseEntity<String> deleteCandidate(Long id) {
//
//        BenchCandidate candidate = benchCandidateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Requirement with id" + id + "not found"));
//        BenchCandidateDTO benchCandidateDTO = entityToDTO.getBenchCandidateDTO(candidate);
//        checkIfAuthorized();
//        candidate.setBenchManager(null);
//        benchCandidateRepository.deleteById(id);
//
//        return ResponseEntity.ok("Successfully deleted bench candidate");
//    }

    private void checkIfAuthorized() {
        UserRole user = userRoleRepository.findByEmailId(jwtService.getUser()).orElse(null);
        if(!authorised_roles.contains(user.getRole())) {
            throw new UserUnauthorisedException("User is not authorised to perform the above operation");
        }
    }
}