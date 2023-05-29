package com.flowiee.app.sanpham.services;

public interface MailService {

    void sendMail(String subject, String to, String body);

}