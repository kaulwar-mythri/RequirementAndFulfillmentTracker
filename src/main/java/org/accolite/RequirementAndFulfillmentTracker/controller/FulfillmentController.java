package org.accolite.RequirementAndFulfillmentTracker.controller;



import org.accolite.RequirementAndFulfillmentTracker.entity.Fulfillment;
import org.accolite.RequirementAndFulfillmentTracker.service.FulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fulfillments")
public class FulfillmentController {

    @Autowired
    FulfillmentService fulfillmentService;

    // Sample data - replace it with a database or any storage mechanism
    private List<Fulfillment> fulfillments;


    @PostMapping("/create")
    public Fulfillment createFulfillment(@RequestBody Fulfillment fulfillment) {

        return fulfillmentService.createFulfillment(fulfillment);
    }

    @PutMapping("/{id}/update")
    public Fulfillment updateFulfillment(@PathVariable Long id, @RequestBody Fulfillment updatedFulfillment) {
        return fulfillmentService.updateFulfillment(id,updatedFulfillment);
    }

    @GetMapping("/all")
    public List<Fulfillment> getAllFulfillments() {
        return fulfillmentService.getAllFulfillments();
    }

    @GetMapping("/{fulfillmentId}")
    public Fulfillment getFulfillmentById(@PathVariable Long fulfillmentId) {
        return fulfillmentService.getFulfillmentById(fulfillmentId);
    }

    @DeleteMapping("/{fulfillmentId}/delete")
    public void deleteFulfillment(@PathVariable Long fulfillmentId) {
        fulfillmentService.deleteFulfillment(fulfillmentId);
    }
}