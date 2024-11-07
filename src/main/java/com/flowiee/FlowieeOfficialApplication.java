package com.flowiee;

import com.google.zxing.WriterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;

@CrossOrigin
@EnableScheduling
@SpringBootApplication
public class FlowieeOfficialApplication {
    private static Logger logger = LoggerFactory.getLogger(FlowieeOfficialApplication.class);

    public static void main(String[] args) throws WriterException, IOException {
        SpringApplication.run(FlowieeOfficialApplication.class, args);
        logger.info("Welcome");
    }

}