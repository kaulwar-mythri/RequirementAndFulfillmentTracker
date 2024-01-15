package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import org.accolite.RequirementAndFulfillmentTracker.entity.Role;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    UserRoleRepository userRoleRepository;
    @Override
    public ResponseEntity<String> approveUserAccess(Long id, Role role) {
        Optional<UserRole> existingUserRole = userRoleRepository.findById(id);

        if (existingUserRole.isPresent()) {
            UserRole userRole = existingUserRole.get();
            userRole.setRole(role);
            userRoleRepository.save(userRole);

            return ResponseEntity.ok("User Role Saved");
        } else {
            return ResponseEntity.badRequest().body("User Role not found");
        }
    }
}
