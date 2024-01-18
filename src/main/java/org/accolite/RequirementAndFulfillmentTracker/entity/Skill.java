package org.accolite.RequirementAndFulfillmentTracker.entity;


import jakarta.persistence.Entity;
<<<<<<< HEAD
=======
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
<<<<<<< HEAD

public class Skill {
    Long skillId;
    Experience experience;
    Technology technology;

=======
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long skillId;
    //    @Enumerated(EnumType.STRING)
    Experience experience;
    //    @Enumerated(EnumType.STRING)
    Technology technology;
>>>>>>> 3d1190ba5eb171819a97d546125ea8c32921b9d2
}
