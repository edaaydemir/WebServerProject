package com.HMSApp.HospitalMngmnt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.HMSApp.HospitalMngmnt.entity.EmailBody;

import jakarta.mail.MessagingException;

@Service
public class EmailServiceImpl implements IEmailService {

    @Autowired
    JavaMailSender JavaMailSender;

    @Override
    public Boolean sendBookingMail(String toEmail, EmailBody emailBody) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("doctordoe01@gmail.com");
        message.setTo(toEmail);
        message.setText(emailBody.getEmailText());
        message.setSubject(emailBody.getEmailHeader());

        JavaMailSender.send(message);

        return true;
    }

}
