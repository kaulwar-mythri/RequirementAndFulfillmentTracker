package org.accolite.RequirementAndFulfillmentTracker.service;

import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserRoleService {
    public List<UserRole> getAllUsers();

    public UserRole getUserById(long id);

    // Update User
    public UserRole updateUser(long id, UserRole updatedUserRole);
    public ResponseEntity<String> deleteUserById(long id);

    public ResponseEntity<String> requestAccess(UserRole userRole);
}
