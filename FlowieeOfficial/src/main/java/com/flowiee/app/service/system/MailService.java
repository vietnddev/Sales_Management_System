package com.flowiee.app.service.system;

public interface MailService {
    String sendMail(String subject, String to, String body);
}