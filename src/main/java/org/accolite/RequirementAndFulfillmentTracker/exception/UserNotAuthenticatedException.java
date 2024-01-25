package org.accolite.RequirementAndFulfillmentTracker.exception;

public class UserNotAuthenticatedException extends RuntimeException {
    public UserNotAuthenticatedException(String msg) {
        super(msg);
    }
}
