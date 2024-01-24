package org.accolite.RequirementAndFulfillmentTracker.model;

import lombok.*;
import org.accolite.RequirementAndFulfillmentTracker.entity.Role;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleDTO {
    long id;
    String employeeId;
    String name;
    String emailId;
    Role role;
    Set<AccountDTO> accounts = new HashSet<>();
}
