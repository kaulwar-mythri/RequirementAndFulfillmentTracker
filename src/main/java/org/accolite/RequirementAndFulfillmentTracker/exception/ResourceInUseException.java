package org.accolite.RequirementAndFulfillmentTracker.exception;

public class ResourceInUseException extends RuntimeException{
    public ResourceInUseException(String message) {
        super(message);
    }
}
