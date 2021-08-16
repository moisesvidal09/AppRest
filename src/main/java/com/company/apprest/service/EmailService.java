package com.company.apprest.service;

import com.company.apprest.entity.model.Email;
import com.company.apprest.enums.StatusEmail;
import com.company.apprest.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(Email email){

        email.setSend(new Date());

        try{

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.getEmailFrom());
            message.setTo(email.getEmailTo());
            message.setSubject(email.getSubject());
            message.setText(email.getText());

            javaMailSender.send(message);

        } catch (MailException e) {
            email.setStatusEmail(StatusEmail.NAO_ENVIADO);
        }

    }

}
