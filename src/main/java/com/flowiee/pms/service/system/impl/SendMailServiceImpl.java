package com.flowiee.pms.service.system.impl;

import com.flowiee.pms.exception.AppException;
import com.flowiee.pms.service.BaseService;
import com.flowiee.pms.service.system.SendMailService;

import com.flowiee.pms.utils.constants.MessageCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SendMailServiceImpl extends BaseService implements SendMailService {
    JavaMailSender javaMailSender;

    @Override
    public String sendMail(String subject, String to, String body) {
        if (ObjectUtils.isEmpty(subject) || ObjectUtils.isEmpty(to) || ObjectUtils.isEmpty(body)) {
            throw new AppException("Invalid mail information!");
        }
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setText(body, true);
            javaMailSender.send(mimeMessage);
            return MessageCode.DELETE_SUCCESS.getDescription();
        } catch (MessagingException ex) {
            throw new AppException(ex);
        }
    }
}