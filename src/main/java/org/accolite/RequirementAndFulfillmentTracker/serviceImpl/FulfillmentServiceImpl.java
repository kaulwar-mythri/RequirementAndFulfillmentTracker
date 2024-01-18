package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import org.accolite.RequirementAndFulfillmentTracker.entity.Fulfillment;
import org.accolite.RequirementAndFulfillmentTracker.exception.ResourceNotFoundException;
import org.accolite.RequirementAndFulfillmentTracker.repository.FulfillmentRepository;
import org.accolite.RequirementAndFulfillmentTracker.service.FulfillmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FulfillmentServiceImpl implements FulfillmentService {

    @Autowired
    FulfillmentRepository fulfillmentRepository;



    private List<Fulfillment> fulfillments;

    @Override
    public Fulfillment createFulfillment(Fulfillment fulfillment) {


        // Save the Fulfillment entity
        return fulfillmentRepository.save(fulfillment);
    }


    @Override
    public List<Fulfillment> getAllFulfillments() {
        return fulfillmentRepository.findAll();
    }

    @Override
    public Fulfillment getFulfillmentById(Long fulfillmentId) {



        return fulfillments.stream()
                .filter(fulfillment -> fulfillment.getFulfillmentId().equals(fulfillmentId))
                .findFirst()
                .orElse(null);


    }

    @Override
    public Fulfillment updateFulfillment(Long fulfillmentID, Fulfillment updatedFulfillment) {
        Fulfillment existingFulfillment = fulfillmentRepository.findById(fulfillmentID)
                .orElseThrow(() -> new ResourceNotFoundException("Fulfillment not found with id: " + fulfillmentID));

        // Update fields of the existing fulfillment with the values from updatedFulfillment
        existingFulfillment.setFulfillmentDate(updatedFulfillment.getFulfillmentDate());
        existingFulfillment.setFulfillmentStatus(updatedFulfillment.getFulfillmentStatus());
        existingFulfillment.setSubmission(updatedFulfillment.getSubmission());

        // Save the updated fulfillment to the database
        return fulfillmentRepository.save(existingFulfillment);
    }

    @Override
    public void deleteFulfillment(Long fulfillmentId) {
        fulfillmentRepository.deleteById(fulfillmentId);
    }
}
