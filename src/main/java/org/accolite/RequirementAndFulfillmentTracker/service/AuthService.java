package org.accolite.RequirementAndFulfillmentTracker.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface AuthService {
    public ResponseEntity<Map<String, Object>> createUserRole(String googleAuthToken);
}
