package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import jakarta.persistence.EntityNotFoundException;

import jakarta.transaction.Transactional;
import org.accolite.RequirementAndFulfillmentTracker.config.JWTService;
import org.accolite.RequirementAndFulfillmentTracker.entity.*;
import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.exception.UserUnauthorisedException;
import org.accolite.RequirementAndFulfillmentTracker.model.AccountDTO;
import org.accolite.RequirementAndFulfillmentTracker.model.BenchCandidateDTO;
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

        UserRole benchManager = userRoleRepository.findById(candidate.getBenchManager().getId()).orElseThrow(() -> new ResourceNotFoundException("Bench manager not found exception"));
        BenchCandidate newCandidate = BenchCandidate.builder()
                .candidateName(candidate.getCandidateName())
                .skills(candidate.getSkills())
                .benchManager(benchManager)
                .benchPeriod(candidate.getBenchPeriod())
                .status(candidate.getCandidateStatus())
                .build();

        newCandidate = benchCandidateRepository.save(newCandidate);

        return ResponseEntity.ok(entityToDTO.getBenchCandidateDTO(newCandidate));
    }

    @Override
    public List<BenchCandidateDTO> getAllCandidates() {
        List<BenchCandidate> benchCandidatesList = benchCandidateRepository.findAll();

        return benchCandidatesList.stream().map(candidate -> entityToDTO.getBenchCandidateDTO(candidate)).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<BenchCandidateDTO> getCandidateById(Long id) {

        BenchCandidate benchCandidate = benchCandidateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bench candidate not found"));

        BenchCandidateDTO benchCandidateDTO = entityToDTO.getBenchCandidateDTO(benchCandidate);
        return ResponseEntity.ok(benchCandidateDTO);
    }

    @Override
    @Transactional
    public ResponseEntity<BenchCandidateDTO> updateCandidate(Long id, BenchCandidateDTO updatedCandidate) {
        checkIfAuthorized();
        BenchCandidate existingCandidate = benchCandidateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Bench Candidate not found"));

        UserRole benchManager = userRoleRepository.findById(updatedCandidate.getBenchManager().getId()).orElseThrow(() -> new ResourceNotFoundException("Bench Manager not found"));

        existingCandidate.setCandidateName(updatedCandidate.getCandidateName());
        existingCandidate.setStatus(updatedCandidate.getCandidateStatus());
//        existingCandidate.setSkills(updatedCandidate.getSkills());
        existingCandidate.setBenchPeriod(updatedCandidate.getBenchPeriod());
        existingCandidate.setBenchManager(benchManager);

        existingCandidate = benchCandidateRepository.save(existingCandidate);

        BenchCandidateDTO benchCandidateDTO = entityToDTO.getBenchCandidateDTO(existingCandidate);
        return ResponseEntity.ok(benchCandidateDTO);
    }

    @Override
    public ResponseEntity<String> deleteCandidate(Long id) {
        checkIfAuthorized();
        benchCandidateRepository.deleteById(id);
        return ResponseEntity.ok("Successfully deleted bench candidate");
    }

    private Set<AccountDTO> convertAccountsToDTOs(Set<Account> accounts) {
        return accounts.stream().map(account -> {
            AccountDTO accountDTO = AccountDTO.builder()
                    .account_id(account.getAccount_id())
                    .name(account.getName())
                    .hierarchyTag(account.getHierarchyTag())
                    .parentId(account.getParentId())
                    .build();
            return accountDTO;
        }).collect(Collectors.toSet());
    }

    private void checkIfAuthorized() {
        UserRole user = userRoleRepository.findByEmailId(jwtService.getUser()).orElse(null);
        if(!authorised_roles.contains(user.getRole())) {
            throw new UserUnauthorisedException("User is not authorised to perform the above operation");
        }
    }
}