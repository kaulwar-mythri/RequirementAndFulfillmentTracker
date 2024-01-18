package org.accolite.RequirementAndFulfillmentTracker.model;

import lombok.*;
import org.accolite.RequirementAndFulfillmentTracker.entity.HierarchyTag;
import org.accolite.RequirementAndFulfillmentTracker.entity.UserRole;

import java.util.HashSet;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDTO {
    long account_id;
    String name;
    long parentId;
    HierarchyTag hierarchyTag;
    Set<UserRoleDTO> userRoleDTOS = new HashSet<>();
}
