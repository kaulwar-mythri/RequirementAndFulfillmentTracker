package org.accolite.RequirementAndFulfillmentTracker.service;


<<<<<<< HEAD
=======
import jakarta.mail.MessagingException;
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
import org.accolite.RequirementAndFulfillmentTracker.entity.Requirement;

import java.util.List;
import java.util.Set;

public interface RequirementService {
<<<<<<< HEAD
     Requirement createRequirement(Requirement requirement);
   Requirement updateRequirement(Long id, Requirement updatedRequirement);
     List<Requirement> getAllRequirements();
     Requirement getRequirementById(Long id);
     void deleteRequirement(Long id);
     void alertBench(Long benchManagerId, Set<Long> requirementIds);
     void alertHiring(Long hiringManagerId, Set<Long> requirementIds);



}
=======
    Requirement createRequirement(Requirement requirement);
    Requirement updateRequirement(Long id, Requirement updatedRequirement);
    List<Requirement> getAllRequirements();
    Requirement getRequirementById(Long id);
    void deleteRequirement(Long id);
    void alertBench(Long benchManagerId, Set<Long> requirementIds) throws MessagingException;
    void alertHiring(Long hiringManagerId, Set<Long> requirementIds);



}
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
