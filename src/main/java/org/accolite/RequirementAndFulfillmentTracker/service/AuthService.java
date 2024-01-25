package org.accolite.RequirementAndFulfillmentTracker.service;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.model.UserRoleDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

public interface AuthService {
    public ResponseEntity<Map<String, Object>> createUserRole(String googleAuthToken);

    public ResponseEntity<UserRoleDTO> getUser();
}
