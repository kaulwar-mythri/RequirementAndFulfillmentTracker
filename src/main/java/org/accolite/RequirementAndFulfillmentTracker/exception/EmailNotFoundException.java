package org.accolite.RequirementAndFulfillmentTracker.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(String msg) {
        super(msg);
    }
}
