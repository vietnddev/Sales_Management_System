package com.flowiee;

import com.flowiee.app.base.StartUp;

import com.google.zxing.WriterException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;

@CrossOrigin
@EnableScheduling
@SpringBootApplication
public class FlowieeOfficialApplication {
//    @Autowired
//    private MailService mailService;

    public static void main(String[] args) throws WriterException, IOException {
        SpringApplication.run(FlowieeOfficialApplication.class, args);
        new StartUp();
//        String data = "https://viblo.asia/p/tao-ma-qr-code-trong-java-voi-zxing-4P856grLKY3";
//        QRCodeWriter qrCodeWriter = new QRCodeWriter();
//        BitMatrix matrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 200, 200);
//
//        // Write to file image
//        String outputFile = "D://image.png";
//        Path path = FileSystems.getDefault().getPath(outputFile);
//        MatrixToImageWriter.writeToPath(matrix, "PNG", path);
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