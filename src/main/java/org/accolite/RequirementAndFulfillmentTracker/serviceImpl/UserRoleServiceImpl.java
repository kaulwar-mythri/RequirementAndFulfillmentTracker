package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import org.accolite.RequirementAndFulfillmentTracker.entity.Role;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.model.UserRoleDTO;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;
//    @Override
//    public ResponseEntity<String> createUserRole(UserRole userRole) {
//        userRole.setRole(Role.DEFAULT);
//        userRoleRepository.save(userRole);
//        requestAccess(userRole);
//        return ResponseEntity.ok("User created");
//    }

    @Override
    public List<UserRole> getAllUsers() {
        return userRoleRepository.findAll();
    }

    @Override
    public UserRole getUserById(long id) {
        return userRoleRepository.findById(id).orElse(null);
    }

    @Override
    public UserRole updateUser(long id, UserRole updatedUserRole) {
        Optional<UserRole> existingUserRole = userRoleRepository.findById(id);

        if (existingUserRole.isPresent()) {
            UserRole userRole = existingUserRole.get();
            userRole.setAccounts(updatedUserRole.getAccounts());
            userRole.setRole(updatedUserRole.getRole());
            userRole.setEmailId(updatedUserRole.getEmailId());
            return userRoleRepository.save(userRole);
        } else {
            //return ResponseEntity.badRequest().body("User Role not found");
            return null;
        }
    }

    @Override
    public ResponseEntity<String> deleteUserById(long id) {
        userRoleRepository.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }

    @Override
    public ResponseEntity<String> requestAccess(UserRoleDTO userRoleDTO) {
        //send alert to admin

        return ResponseEntity.ok("Requested Access");
    }
}
