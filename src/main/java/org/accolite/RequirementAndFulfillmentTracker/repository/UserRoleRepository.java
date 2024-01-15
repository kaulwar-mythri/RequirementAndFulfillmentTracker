package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByEmailId(String email);
}
