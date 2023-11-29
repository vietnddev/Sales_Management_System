package com.flowiee.app.service.impl;

import com.flowiee.app.service.MailService;

import com.flowiee.app.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendMail(String subject, String to, String body) {
        if (subject != null && to != null && body != null) {
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setSubject(subject);
                helper.setTo(to);
                helper.setText(body, true);
                javaMailSender.send(mimeMessage);
                return AppConstants.SERVICE_RESPONSE_SUCCESS;
            } catch (MessagingException exception) {
                exception.printStackTrace();
            }
        }
        return AppConstants.SERVICE_RESPONSE_FAIL;
    }
}