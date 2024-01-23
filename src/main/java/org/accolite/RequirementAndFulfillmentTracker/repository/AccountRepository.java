package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findById(long id);
}
