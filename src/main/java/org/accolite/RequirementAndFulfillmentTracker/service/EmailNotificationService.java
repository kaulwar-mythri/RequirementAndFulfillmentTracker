package org.accolite.RequirementAndFulfillmentTracker.service;


import jakarta.mail.MessagingException;

public interface EmailNotificationService {

    public void sendNotification(String to, String subject, String text) throws MessagingException, MessagingException;
}
