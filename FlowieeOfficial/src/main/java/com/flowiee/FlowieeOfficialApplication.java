package com.flowiee;

import com.flowiee.app.hethong.service.MailService;
import com.flowiee.app.khotailieu.entity.Document;
import com.flowiee.app.khotailieu.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin
@EnableScheduling
@SpringBootApplication
public class FlowieeOfficialApplication {
    @Autowired
    private MailService mailService;

    public static void main(String[] args) {
        SpringApplication.run(FlowieeOfficialApplication.class, args);

//        int year = 2023; // Năm cần in danh sách tuần
//
//        LocalDate firstDayOfYear = LocalDate.of(year, 1, 1);
//        while (firstDayOfYear.getDayOfWeek() != DayOfWeek.MONDAY) {
//            firstDayOfYear = firstDayOfYear.plusDays(1);
//        }
//
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        for (int i = 0; i < 52; i++) { // Duyệt qua 52 tuần của năm (giả sử năm có 52 tuần)
//            LocalDate startOfWeek = firstDayOfYear.plusWeeks(i);
//            LocalDate endOfWeek = startOfWeek.plusDays(6);
//
//            // Kiểm tra tuần thuộc tháng nào
//            Month startMonth = startOfWeek.getMonth();
//            Month endMonth = endOfWeek.getMonth();
//            String monthName = startMonth == endMonth ? startMonth.toString() : startMonth.toString() + " - " + endMonth.toString();
//
//            System.out.println("Tuần " + (i + 1) + ": " + startOfWeek.format(dateFormatter)
//                    + " - " + endOfWeek.format(dateFormatter)
//                    + " (Thuộc tháng: " + monthName + ")");
        //}
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