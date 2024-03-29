package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import org.accolite.RequirementAndFulfillmentTracker.config.JWTService;
import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.Role;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.exception.UserUnauthorisedException;
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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    EntityToDTO entityToDTO;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JWTService jwtService;
    List<Role> authorised_roles = new ArrayList<>(List.of(Role.SUPER_ADMIN));
//    @Override
//    public ResponseEntity<String> createUserRole(UserRole userRole) {
//        userRole.setRole(Role.DEFAULT);
//        userRoleRepository.save(userRole);
//        requestAccess(userRole);
//        return ResponseEntity.ok("User created");
//    }

    @Override
    public ResponseEntity<List<UserRoleDTO>> getAllUsers() {
        Role user_role = userRoleRepository.findByEmailId(SecurityContextHolder.getContext().getAuthentication().getName()).orElse(null).getRole();
        if(user_role == Role.SUPER_ADMIN || user_role == Role.ADMIN || user_role == Role.BENCH_MANAGER) {
            return ResponseEntity.ok(userRoleRepository.findAll().stream().map(userRole -> {
                return entityToDTO.getUserRoleDTO(userRole);
            }).toList());
        } else {
            throw new UserUnauthorisedException("User is not authorised to perform the above operation");
        }

    }

    @Override
    public ResponseEntity<UserRoleDTO> getUserByEmployeeId(long employeeId) {
        checkIfAuthorized();
        return ResponseEntity.ok(entityToDTO.getUserRoleDTO(userRoleRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("User with given id not found"))));
    }

    @Override
    public ResponseEntity<UserRoleDTO> updateUser(long employeeId, UserRoleDTO updatedUserRole) {
        checkIfAuthorized();
        UserRole existingUserRole = userRoleRepository.findByEmployeeId(employeeId)
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
    public ResponseEntity<String> deleteUserByEmployeeId(long employeeId) {
        checkIfAuthorized();
        userRoleRepository.deleteById(employeeId);
        return ResponseEntity.ok("User deleted");
    }

    @Override
    public ResponseEntity<String> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getAuthorities());

        return ResponseEntity.ok(authentication.getName());
    }

    private void checkIfAuthorized() {
        UserRole user = userRoleRepository.findByEmailId(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if(!authorised_roles.contains(user.getRole()))
            throw new UserUnauthorisedException("User is not authorised to perform the above operation");
    }
}
