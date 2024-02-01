package org.accolite.RequirementAndFulfillmentTracker.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FirebasePayloadDTO {
    String name;
    String emailId;
}
