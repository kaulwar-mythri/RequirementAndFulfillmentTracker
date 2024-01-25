package org.accolite.RequirementAndFulfillmentTracker.controller;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.model.UserRoleDTO;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.AuthService;
import org.accolite.RequirementAndFulfillmentTracker.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/createUser")
    public ResponseEntity<Map<String, Object>> createUserRole(@RequestBody String googleAuthToken) {
        return authService.createUserRole(googleAuthToken);
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserRoleDTO> getUser() {
        return authService.getUser();
    }
}

