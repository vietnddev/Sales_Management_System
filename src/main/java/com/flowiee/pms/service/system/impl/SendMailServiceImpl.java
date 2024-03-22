package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.service.system.SendMailService;

import com.flowiee.pms.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendMailServiceImpl implements SendMailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendMail(String subject, String to, String body) {
        if (subject != null && to != null && body != null) {
            throw new AppException();
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setText(body, true);
            javaMailSender.send(mimeMessage);
            return MessageUtils.DELETE_SUCCESS;
        } catch (MessagingException ex) {
            throw new AppException(ex);
        }
    }
}