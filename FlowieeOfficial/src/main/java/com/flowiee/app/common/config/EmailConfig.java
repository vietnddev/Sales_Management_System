//package com.flowiee.app.common.config;
//
//import com.flowiee.app.hethong.service.FlowieeConfigService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//@Configuration
//public class EmailConfig {
//    @Autowired
//    private FlowieeConfigService configService;
//
//    @Bean
//    public JavaMailSender javaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("");
//        mailSender.setPort(Integer.parseInt(""));
//        mailSender.setUsername("");
//        mailSender.setPassword("");
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.smtp.auth", true);
//        props.put("mail.smtp.starttls.enable", true);
//
//        return mailSender;
//    }
//}
