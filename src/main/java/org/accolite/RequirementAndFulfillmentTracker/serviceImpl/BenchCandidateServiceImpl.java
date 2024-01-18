package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import jakarta.persistence.EntityNotFoundException;

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
        return benchCandidateRepository.save(candidate);
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
    public BenchCandidate updateCandidate(Long id, BenchCandidate updatedCandidate) {
        // Find the existing BenchCandidate by ID
        Optional<BenchCandidate> optionalExistingCandidate = benchCandidateRepository.findById(id);

        if (optionalExistingCandidate.isPresent()) {
            BenchCandidate existingCandidate = optionalExistingCandidate.get();

            // Update the fields based on the provided data in updatedCandidate
            existingCandidate.setStatus(updatedCandidate.getStatus());
            existingCandidate.setSkill(updatedCandidate.getSkill());
            existingCandidate.setBenchPeriod(updatedCandidate.getBenchPeriod());
            existingCandidate.setBenchManager(updatedCandidate.getBenchManager());



            // Save the updated BenchCandidatexz
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
