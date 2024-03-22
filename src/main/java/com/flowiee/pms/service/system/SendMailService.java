package com.flowiee.pms.service.system;

public interface SendMailService {
    String sendMail(String subject, String to, String body);
}