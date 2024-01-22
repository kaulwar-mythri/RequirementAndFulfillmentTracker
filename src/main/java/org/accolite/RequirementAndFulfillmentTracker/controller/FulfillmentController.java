package org.accolite.RequirementAndFulfillmentTracker.controller;



import org.accolite.RequirementAndFulfillmentTracker.entity.Fulfillment;
import org.accolite.RequirementAndFulfillmentTracker.model.FulfillmentDTO;
import org.accolite.RequirementAndFulfillmentTracker.service.FulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fulfillments")
@CrossOrigin(origins="http://localhost:4200")
public class FulfillmentController {

    @Autowired
    FulfillmentService fulfillmentService;

    @PostMapping("/create")
    public ResponseEntity<FulfillmentDTO> createFulfillment(@RequestBody FulfillmentDTO fulfillment) {

        return fulfillmentService.createFulfillment(fulfillment);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<FulfillmentDTO> updateFulfillment(@PathVariable Long id, @RequestBody FulfillmentDTO updatedFulfillment) {
        return fulfillmentService.updateFulfillment(id, updatedFulfillment);
    }

    @GetMapping("/all")
    public ResponseEntity<List<FulfillmentDTO>> getAllFulfillments() {
        return fulfillmentService.getAllFulfillments();
    }

    @GetMapping("/{fulfillmentId}")
    public ResponseEntity<FulfillmentDTO> getFulfillmentById(@PathVariable Long fulfillmentId) {
        return fulfillmentService.getFulfillmentById(fulfillmentId);
    }

    @DeleteMapping("/{fulfillmentId}/delete")
    public void deleteFulfillment(@PathVariable Long fulfillmentId) {
        fulfillmentService.deleteFulfillment(fulfillmentId);
    }
}