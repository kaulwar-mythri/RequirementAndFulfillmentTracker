package org.accolite.RequirementAndFulfillmentTracker.repository;

import org.accolite.RequirementAndFulfillmentTracker.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
