package org.accolite.RequirementAndFulfillmentTracker.service;

import org.accolite.RequirementAndFulfillmentTracker.entity.Role;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.springframework.http.ResponseEntity;

public interface AdminService {
    public ResponseEntity<String> approveUserAccess(Long id, Role role);
}
