package com.flowiee.app.common.utils;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CurrencyUtil {
    public static String formatToVND(long currency) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        currencyFormat.setCurrency(Currency.getInstance("VND"));
        return currencyFormat.format(currency);
    }

    public static String formatToVND(float currency) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        currencyFormat.setCurrency(Currency.getInstance("VND"));
        return currencyFormat.format(currency);
    }

    public static String formatToVND(double currency) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        currencyFormat.setCurrency(Currency.getInstance("VND"));
        return currencyFormat.format(currency);
    }
}