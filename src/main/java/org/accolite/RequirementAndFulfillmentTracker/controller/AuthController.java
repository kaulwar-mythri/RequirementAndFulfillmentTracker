package org.accolite.RequirementAndFulfillmentTracker.controller;

import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.repository.UserRoleRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.AuthService;
import org.accolite.RequirementAndFulfillmentTracker.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthService authService;
    @PostMapping("/createUser")
    public ResponseEntity<Map<String, Object>> createUserRole(@RequestBody String googleAuthToken) {
        return authService.createUserRole(googleAuthToken);
    }
}

