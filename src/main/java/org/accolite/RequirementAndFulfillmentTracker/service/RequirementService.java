package org.accolite.RequirementAndFulfillmentTracker.service;


import jakarta.mail.MessagingException;
import org.accolite.RequirementAndFulfillmentTracker.entity.Requirement;
import org.accolite.RequirementAndFulfillmentTracker.model.RequirementDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface RequirementService {

    ResponseEntity<RequirementDTO> createRequirement(RequirementDTO requirement) throws MessagingException;
    ResponseEntity<RequirementDTO> updateRequirement(Long id, RequirementDTO updatedRequirement);
    ResponseEntity<List<RequirementDTO>> getAllRequirements();

    ResponseEntity<RequirementDTO> getRequirementById(Long id);

//    void deleteRequirement(Long id);
    void alertBench(Long benchManagerId, Set<Long> requirementIds) throws MessagingException;
    void alertHiring(Long hiringManagerId, Set<Long> requirementIds) throws MessagingException;



}
