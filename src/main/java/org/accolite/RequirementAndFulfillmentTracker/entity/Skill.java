package org.accolite.RequirementAndFulfillmentTracker.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long skillId;
    //    @Enumerated(EnumType.STRING)
    Experience experience;
    //    @Enumerated(EnumType.STRING)
    Technology technology;
}
