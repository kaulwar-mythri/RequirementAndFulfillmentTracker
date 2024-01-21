package org.accolite.RequirementAndFulfillmentTracker.entity;


import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.apache.catalina.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long account_id;
    String name;
    long parentId;

    @Enumerated(EnumType.STRING)
    HierarchyTag hierarchyTag;

    @ManyToMany(mappedBy = "accounts", fetch = FetchType.EAGER, cascade = CascadeType.ALL)

    Set<UserRole> userRoles = new HashSet<>();

    public long getId() {
        return account_id;
    }

    public void setId(long id) {
        this.account_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public HierarchyTag getHierarchyTag() {
        return hierarchyTag;
    }

    public void setHierarchyTag(HierarchyTag hierarchyTag) {
        this.hierarchyTag = hierarchyTag;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
