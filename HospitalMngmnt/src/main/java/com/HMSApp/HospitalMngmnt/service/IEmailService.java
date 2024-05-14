package com.HMSApp.HospitalMngmnt.service;

import com.HMSApp.HospitalMngmnt.entity.EmailBody;

import jakarta.mail.MessagingException;

public interface IEmailService {

    Boolean sendBookingMail(String toEmail, EmailBody emailBody) throws MessagingException;

}
