package org.accolite.RequirementAndFulfillmentTracker.controller;

import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.model.UserRoleDTO;
import org.accolite.RequirementAndFulfillmentTracker.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/userRoles")
public class UserRoleController {
    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/all")
    public ResponseEntity<List<UserRoleDTO>> getAllUsers() {
        System.out.println("hello from getAllUsers");
        return userRoleService.getAllUsers();
    }
    @GetMapping("/{employeeId}")
    public ResponseEntity<UserRoleDTO> getUserByEmployeeId(@PathVariable long employeeId) {
        return userRoleService.getUserByEmployeeId(employeeId);
    }

    @PutMapping("/{employeeId}/update")
    public ResponseEntity<UserRoleDTO> updateUser(@PathVariable long employeeId, @RequestBody UserRoleDTO updatedUserRole) {
        return userRoleService.updateUser(employeeId, updatedUserRole);
    }

    @DeleteMapping("/{employeeId}/delete")
    public ResponseEntity<String> deleteUserByEmployeeId(@PathVariable long employeeId) {
        return userRoleService.deleteUserByEmployeeId(employeeId);
    }

    @PostMapping("/requestAccess")
    public ResponseEntity<String> requestAccess(@RequestBody UserRoleDTO userRoleDTO) {
        return userRoleService.requestAccess(userRoleDTO);
    }
}
