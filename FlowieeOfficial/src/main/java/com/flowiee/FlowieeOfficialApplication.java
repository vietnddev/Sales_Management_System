package com.flowiee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class FlowieeOfficialApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlowieeOfficialApplication.class, args);
    }
}
