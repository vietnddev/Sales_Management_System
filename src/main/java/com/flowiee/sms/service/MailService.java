package com.flowiee.sms.service;

public interface MailService {
    String sendMail(String subject, String to, String body);
}