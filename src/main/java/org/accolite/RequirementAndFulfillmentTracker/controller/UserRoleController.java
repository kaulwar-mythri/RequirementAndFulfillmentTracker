package org.accolite.RequirementAndFulfillmentTracker.controller;

import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userRoles")
public class UserRoleController {
    @Autowired
    UserRoleService userRoleService;

    @GetMapping("/")
    public List<UserRole> getAllUsers() {
        return userRoleService.getAllUsers();
    }
    @GetMapping("/{id}")
    public UserRole getUserById(@PathVariable long id) {
        return userRoleService.getUserById(id);
    }

    @PutMapping("/{id}/update")
    public UserRole updateUser(@PathVariable long id, @RequestBody UserRole updatedUserRole) {
        return userRoleService.updateUser(id, updatedUserRole);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> deleteUserById(@PathVariable long id) {
        return userRoleService.deleteUserById(id);
    }

    @PostMapping("/requestAccess")
    public ResponseEntity<String> requestAccess(@RequestBody UserRole userRole) {
        return userRoleService.requestAccess(userRole);
    }
}
