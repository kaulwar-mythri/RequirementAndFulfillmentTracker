package org.accolite.RequirementAndFulfillmentTracker.service;

import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.model.UserRoleDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRoleService {
    public ResponseEntity<List<UserRoleDTO>> getAllUsers();

    public ResponseEntity<UserRoleDTO> getUserByEmployeeId(long employeeId);

    // Update User
    public ResponseEntity<UserRoleDTO> updateUser(long employeeId, UserRoleDTO updatedUserRole);
    public ResponseEntity<String> deleteUserByEmployeeId(long employeeId);
    public ResponseEntity<String> getUser();
}
