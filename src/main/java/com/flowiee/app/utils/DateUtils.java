package com.flowiee.app.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String convertDateToString(String formatInput, String formatOutput, Date timeInput) {
        String outputTime = null;
        try {
            if (!formatInput.isEmpty() && !formatOutput.isEmpty() && timeInput != null) {
                SimpleDateFormat inputFormat = new SimpleDateFormat(formatInput);
                SimpleDateFormat outputFormat = new SimpleDateFormat(formatOutput);
                Date date = inputFormat.parse(timeInput.toString());
                outputTime = outputFormat.format(date);
                return outputTime;
            }
        } catch (ParseException e) {
            System.out.println("Error occurred while parsing date: " + e.getMessage());
        }
        return outputTime;
    }

    public static Date convertStringToDate(String dateString) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date date = null;
        try {
            if (!dateString.isEmpty()) {
                date = dateFormat.parse(dateString);
            }
        } catch (ParseException e) {
            System.out.println("Error occurred while parsing date: " + e.getMessage());
        }
        return date;
    }

    public static Date convertStringToDate(String dateString, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            if (dateString != null) {
                date = dateFormat.parse(dateString);
            }
        } catch (ParseException e) {
            System.out.println("Error occurred while parsing date: " + e.getMessage());
        }
        return date;
    }

    public static Date formatDate(Date date, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            String formattedDateStr = sdf.format(date);
            return sdf.parse(formattedDateStr);
        } catch (ParseException e) {
            System.out.println(e.getCause());
            e.printStackTrace();
            return date;
        }
    }
}