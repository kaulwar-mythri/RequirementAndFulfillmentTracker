package org.accolite.RequirementAndFulfillmentTracker.controller;

import org.accolite.RequirementAndFulfillmentTracker.entity.Role;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.accolite.RequirementAndFulfillmentTracker.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/admins")

public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping("/approveUser")
    public ResponseEntity<String> approveUserAccess(@RequestBody UserRole userRole) {
        return adminService.approveUserAccess(userRole);
    }
}
