package org.accolite.RequirementAndFulfillmentTracker.service;

import com.mysql.cj.exceptions.StreamingNotifiable;
import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.model.UserRoleDTO;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.Set;

public interface AuthService {
    public ResponseEntity<Map<String, Object>> createUserRole(String googleAuthToken);

    public ResponseEntity<Map<String, Object>> signInWithGoogle(String name, String emailId);
    public ResponseEntity<UserRoleDTO> getUser();
}
