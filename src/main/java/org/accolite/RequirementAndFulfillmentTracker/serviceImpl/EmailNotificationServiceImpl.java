package org.accolite.RequirementAndFulfillmentTracker.serviceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.accolite.RequirementAndFulfillmentTracker.service.EmailNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationServiceImpl implements EmailNotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendNotification(String to, String subject, String text) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,false, "utf-8");
        helper.setText("Hello, please find the requirement details below: <br>" + text,  true);
//        MimeMessageHelper helper = new MimeMessageHelper(message, false, "utf-8");
//        mimeMessage.setContent(text, "text/html");

        helper.setTo(to);
        helper.setSubject(subject);
//        helper.setText(text);

//        message.setFrom("raft.user.from@gmail.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);

        javaMailSender.send(mimeMessage);
        System.out.println("Sent Mail...");
    }
}
