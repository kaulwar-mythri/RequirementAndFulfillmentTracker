package org.accolite.RequirementAndFulfillmentTracker.entity;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Skill {
    Long skillId;
    Experience experience;
    Technology technology;

}
