package org.accolite.RequirementAndFulfillmentTracker.service;

import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.model.UserRoleDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRoleService {
    public ResponseEntity<List<UserRoleDTO>> getAllUsers();

    public ResponseEntity<UserRoleDTO> getUserById(long id);

    // Update User
    public ResponseEntity<UserRoleDTO> updateUser(long id, UserRoleDTO updatedUserRole);
    public ResponseEntity<String> deleteUserById(long id);

    public ResponseEntity<String> requestAccess(UserRoleDTO userRoleDTO);

    public ResponseEntity<String> getUser();
}
