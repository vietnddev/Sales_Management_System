package com.flowiee.app.system.service;

public interface MailService {
    String sendMail(String subject, String to, String body);
}