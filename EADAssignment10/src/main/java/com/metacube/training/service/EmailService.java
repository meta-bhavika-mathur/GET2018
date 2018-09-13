package com.metacube.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService
{
    @Autowired
    public JavaMailSender emailSender;
     /**
     * Sends email
     */
   
    public void sendEmail(String to, String subject, String message)
    {
        SimpleMailMessage email = new SimpleMailMessage(); 
        email.setFrom("xyz@gmail.com");
        email.setTo(to); 
        email.setSubject(subject); 
        email.setText(message);
        emailSender.send(email);
    }
}