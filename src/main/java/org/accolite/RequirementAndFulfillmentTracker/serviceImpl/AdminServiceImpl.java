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
    public ResponseEntity<String> approveUserAccess(UserRole userRole) {
        Optional<UserRole> existingUserRole = userRoleRepository.findByEmailId(userRole.getEmailId());

        if (existingUserRole.isPresent()) {
            UserRole user = existingUserRole.get();
            user.setRole(userRole.getRole());
            user.setAccounts(userRole.getAccounts());
            userRoleRepository.save(user);

            return ResponseEntity.ok("User Role Saved");
        } else {
            return ResponseEntity.badRequest().body("User Role not found");
        }
    }
}
