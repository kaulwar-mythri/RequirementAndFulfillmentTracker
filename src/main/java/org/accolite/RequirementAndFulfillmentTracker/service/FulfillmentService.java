package org.accolite.RequirementAndFulfillmentTracker.service;

import org.accolite.RequirementAndFulfillmentTracker.entity.Fulfillment;
import org.accolite.RequirementAndFulfillmentTracker.model.FulfillmentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FulfillmentService {

    ResponseEntity<FulfillmentDTO> createFulfillment(FulfillmentDTO fulfillment);

    ResponseEntity<List<FulfillmentDTO>> getAllFulfillments();

    ResponseEntity<FulfillmentDTO> updateFulfillment(Long fulfillmentID, FulfillmentDTO updatedFulfillment);

//    void deleteFulfillment(@PathVariable Long fulfillmentId);
}
