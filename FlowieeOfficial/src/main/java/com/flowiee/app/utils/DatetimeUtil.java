package com.flowiee.app.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatetimeUtil {
    public static String now(){
        /*
        * Hàm này hỗ trợ lấy giờ gian hiện tại theo định dạng yyyy/MM/dd hh:mm:ss (2023/02/22 21:30:00)
        * */
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dateTimeFormatter.format(now);
    }
}
