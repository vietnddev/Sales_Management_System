package com.flowiee.app.service;

public interface MailService {
    String sendMail(String subject, String to, String body);
}