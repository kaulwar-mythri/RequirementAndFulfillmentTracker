package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.Role;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.model.UserRoleDTO;
import org.accolite.RequirementAndFulfillmentTracker.repository.AccountRepository;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.UserRoleService;
import org.accolite.RequirementAndFulfillmentTracker.utils.EntityToDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    EntityToDTO entityToDTO;
    @Autowired
    AccountRepository accountRepository;
//    @Override
//    public ResponseEntity<String> createUserRole(UserRole userRole) {
//        userRole.setRole(Role.DEFAULT);
//        userRoleRepository.save(userRole);
//        requestAccess(userRole);
//        return ResponseEntity.ok("User created");
//    }

    @Override
    public ResponseEntity<List<UserRoleDTO>> getAllUsers() {
        return ResponseEntity.ok(userRoleRepository.findAll().stream().map(userRole -> {
            return entityToDTO.getUserRoleDTO(userRole);
        }).toList());
    }

    @Override
    public ResponseEntity<UserRoleDTO> getUserById(long id) {
        return ResponseEntity.ok(entityToDTO.getUserRoleDTO(userRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id not found"))));
    }

    @Override
    public ResponseEntity<UserRoleDTO> updateUser(long id, UserRoleDTO updatedUserRole) {
        UserRole existingUserRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Set<Account> userAccounts = updatedUserRole.getAccounts().stream().map(accountDTO -> {
            return accountRepository.findById(accountDTO.getAccount_id()).orElseThrow(() -> new ResourceNotFoundException("Account not found"));
        }).collect(Collectors.toSet());

        existingUserRole.setAccounts(userAccounts);
        existingUserRole.setRole(updatedUserRole.getRole());
        existingUserRole.setEmailId(updatedUserRole.getEmailId());
        existingUserRole.setEmployeeId(updatedUserRole.getEmployeeId());
        existingUserRole.setName(updatedUserRole.getName());
        existingUserRole = userRoleRepository.save(existingUserRole);

        return ResponseEntity.ok(entityToDTO.getUserRoleDTO(existingUserRole));
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

    @Override
    public ResponseEntity<String> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());

        return ResponseEntity.ok(authentication.getName());
    }
}
