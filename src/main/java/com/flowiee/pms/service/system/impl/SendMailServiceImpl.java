package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.system.SendMailService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SendMailServiceImpl extends BaseService implements SendMailService {
    JavaMailSender mvJavaMailSender;

    @Override
    public boolean sendMail(String subject, String to, String body) throws UnsupportedEncodingException, MessagingException {
        Assert.isNull(subject, "Subject cannot be null!");
        Assert.isNull(to, "Recipient cannot be null!");
        Assert.isNull(body, "Content cannot be null!");

        MimeMessage mimeMessage = mvJavaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

        messageHelper.setSubject(subject);
        messageHelper.setTo(to);
        messageHelper.setText(body, true);

        mvJavaMailSender.send(mimeMessage);

        return true;
    }
}