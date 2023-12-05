package com.flowiee;

import com.flowiee.app.base.StartUp;
import com.flowiee.app.service.MailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@CrossOrigin
@EnableScheduling
@SpringBootApplication
public class FlowieeOfficialApplication {
//    @Autowired
//    private MailService mailService;

    public static void main(String[] args) {
        SpringApplication.run(FlowieeOfficialApplication.class, args);
        new StartUp();
    }

    //Auto gửi email báo cáo doanh thu hàng ngày
//    @Scheduled(cron = "0 0 20 * * ?")
//    public void sendReportDaily() {
//        try {
//            String subject = "Email subject";
//            String to = "nguyenducviet0684@gmail.com";
//            String content = "Email content";
//            mailService.sendMail(subject, to, content);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}