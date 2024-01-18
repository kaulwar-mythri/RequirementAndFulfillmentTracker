package org.accolite.RequirementAndFulfillmentTracker.service;


import jakarta.mail.MessagingException;
import org.accolite.RequirementAndFulfillmentTracker.entity.Requirement;

import java.util.List;
import java.util.Set;

public interface RequirementService {
    Requirement createRequirement(Requirement requirement);
    Requirement updateRequirement(Long id, Requirement updatedRequirement);
    List<Requirement> getAllRequirements();
    Requirement getRequirementById(Long id);
    void deleteRequirement(Long id);
    void alertBench(Long benchManagerId, Set<Long> requirementIds) throws MessagingException;
    void alertHiring(Long hiringManagerId, Set<Long> requirementIds);



}