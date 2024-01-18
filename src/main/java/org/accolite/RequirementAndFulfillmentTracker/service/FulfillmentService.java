package org.accolite.RequirementAndFulfillmentTracker.service;

import org.accolite.RequirementAndFulfillmentTracker.entity.Fulfillment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface FulfillmentService {

    Fulfillment createFulfillment(Fulfillment fulfillment);

    List<Fulfillment> getAllFulfillments();

    Fulfillment getFulfillmentById(Long fulfillmentId);

    Fulfillment updateFulfillment(Long fulfillmentID, Fulfillment updatedFulfillment);

    void deleteFulfillment(@PathVariable Long fulfillmentId);
}
