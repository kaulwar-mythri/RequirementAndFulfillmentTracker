package org.accolite.RequirementAndFulfillmentTracker.exception;

public class UserUnauthorisedException extends RuntimeException{
    public UserUnauthorisedException(String msg) {
        super(msg);
    }
}
