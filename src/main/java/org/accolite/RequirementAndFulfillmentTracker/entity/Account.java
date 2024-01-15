package org.accolite.RequirementAndFulfillmentTracker.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
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

    @ManyToMany(mappedBy = "accounts", targetEntity = UserRole.class)
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

    @Override
    public String toString() {
        return "Account{" +
                "id=" + account_id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", hierarchyTag=" + hierarchyTag +
                ", userRoles=" + userRoles +
                '}';
    }
}
