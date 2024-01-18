package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import jakarta.persistence.EntityNotFoundException;

import jakarta.transaction.Transactional;
import org.accolite.RequirementAndFulfillmentTracker.entity.CandidateStatus;
import org.accolite.RequirementAndFulfillmentTracker.repository.BenchCandidateRepository;
import org.accolite.RequirementAndFulfillmentTracker.entity.BenchCandidate;
import org.accolite.RequirementAndFulfillmentTracker.service.BenchCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BenchCandidateServiceImpl implements BenchCandidateService {

    @Autowired
    private BenchCandidateRepository benchCandidateRepository;
    @Override
    public BenchCandidate addCandidate(BenchCandidate candidate) {
        BenchCandidate newCandidate = BenchCandidate.builder()
                .candidateName(candidate.getCandidateName())
                .skill(candidate.getSkill())
                .benchManager(candidate.getBenchManager())
                .benchPeriod(candidate.getBenchPeriod())
                .status(candidate.getStatus())
                .build();
        return benchCandidateRepository.save(newCandidate);
    }

    @Override
    public List<BenchCandidate> getAllCandidates() {
        return benchCandidateRepository.findAll();
    }

    @Override
    public Optional<BenchCandidate> getCandidateById(Long id) {
        return benchCandidateRepository.findById(id);
    }

    @Override
    @Transactional
    public BenchCandidate updateCandidate(Long id, BenchCandidate updatedCandidate) {
        // Find the existing BenchCandidate by ID
        Optional<BenchCandidate> optionalExistingCandidate = benchCandidateRepository.findById(id);
        System.out.println(optionalExistingCandidate);
        if (optionalExistingCandidate.isPresent()) {

            BenchCandidate existingCandidate = optionalExistingCandidate.get();
            System.out.println(optionalExistingCandidate);
            System.out.println("Current candidate details = " + existingCandidate);
            // Update the fields based on the provided data in updatedCandidate

            BenchCandidate candidate = BenchCandidate.builder()
                    .candidateName(existingCandidate.getCandidateName())
                    .skill(existingCandidate.getSkill())
                    .benchManager(existingCandidate.getBenchManager())
                    .benchPeriod(existingCandidate.getBenchPeriod())
                    .status(existingCandidate.getStatus())
                    .build();
            existingCandidate.setStatus(updatedCandidate.getStatus());
            existingCandidate.setSkill(updatedCandidate.getSkill());
            existingCandidate.setBenchPeriod(updatedCandidate.getBenchPeriod());
            existingCandidate.setBenchManager(updatedCandidate.getBenchManager());

//            existingCandidate.setBenchManager(updatedCandidate.getBenchManager());
            System.out.println("Updated candidate details" + existingCandidate);
            // Save the updated BenchCandidate
            return benchCandidateRepository.save(existingCandidate);
        } else {
            // Handling the case when the candidate with the given ID is not found

            throw new EntityNotFoundException("BenchCandidate with ID " + id + " not found.");
        }

    }

    @Override
    public void deleteCandidate(Long id) {
        benchCandidateRepository.deleteById(id);
    }
}