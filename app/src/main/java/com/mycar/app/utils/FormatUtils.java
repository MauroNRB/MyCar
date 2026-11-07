package com.mycar.app.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtils {

    private static final Locale PT_BR = new Locale("pt", "BR");

    public static String formatDate(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", PT_BR);
        return sdf.format(date);
    }

    public static String formatDateTime(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", PT_BR);
        return sdf.format(date);
    }

    public static String getCurrentDateTimeString() {
        return formatDateTime(new Date());
    }

    public static String getCurrentDateString() {
        return formatDate(new Date());
    }

    public static String formatCurrency(double value) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(PT_BR);
        DecimalFormat df = new DecimalFormat("R$ #,##0.00", symbols);
        return df.format(value);
    }

    public static String formatOdometer(double km) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(PT_BR);
        DecimalFormat df = new DecimalFormat("#,##0", symbols);
        return df.format(km) + " km";
    }

    public static String formatDistance(double km) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(PT_BR);
        DecimalFormat df = new DecimalFormat("#,##0.0", symbols);
        return df.format(km) + " km";
    }

    public static String formatSpeed(float speedKmh) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(PT_BR);
        DecimalFormat df = new DecimalFormat("0", symbols);
        return df.format(speedKmh);
    }

    public static String formatTimeSeconds(double seconds) {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(PT_BR);
        DecimalFormat df = new DecimalFormat("0.00", symbols);
        return df.format(seconds) + " s";
    }

    public static double msToKmh(float speedMs) {
        return speedMs * 3.6f;
    }
}
