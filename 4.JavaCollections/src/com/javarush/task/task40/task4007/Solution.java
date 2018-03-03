package com.javarush.task.task40.task4007;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/* 
Работа с датами
*/

public class Solution {
    private final static String FORMAT_1 = "d.M.yyyy H:m:s";
    private final static String FORMAT_2 = "d.M.yyyy";
    private final static String FORMAT_3 = "H:m:s";

    public static void main(String[] args) {
        printDate("21.4.2014 15:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf;

        try {
            sdf = new SimpleDateFormat(FORMAT_1, Locale.ENGLISH);
            cal.setTime(sdf.parse(date));
            printDate(cal);
            printTime(cal);
            return;
        } catch (ParseException ignored) {}

        try {
            sdf = new SimpleDateFormat(FORMAT_2, Locale.ENGLISH);
            cal.setTime(sdf.parse(date));
            printDate(cal);
            return;
        } catch (ParseException ignored) {}

        try {
            sdf = new SimpleDateFormat(FORMAT_3, Locale.ENGLISH);
            cal.setTime(sdf.parse(date));
            printTime(cal);
            return;
        } catch (ParseException ignored) {}
    }

    private static void printDate(Calendar cal) {
        System.out.println("День: " + cal.get(Calendar.DAY_OF_MONTH));
        System.out.println("День недели: " + (cal.get(Calendar.DAY_OF_WEEK) - 1 == 0 ? 7 : cal.get(Calendar.DAY_OF_WEEK) - 1));
        System.out.println("День месяца: " + cal.get(Calendar.DAY_OF_MONTH));
        System.out.println("День года: " + cal.get(Calendar.DAY_OF_YEAR));
        System.out.println("Неделя месяца: " + (cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) + 1));
        System.out.println("Неделя года: " + cal.get(Calendar.WEEK_OF_YEAR));
        System.out.println("Месяц: " + (cal.get(Calendar.MONTH)));
        System.out.println("Год: " + cal.get(Calendar.YEAR));
    }

    private static void printTime(Calendar cal) {
        System.out.println("AM или PM: " + (cal.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM"));
        System.out.println("Часы: " + cal.get(Calendar.HOUR));
        System.out.println("Часы дня: " + cal.get(Calendar.HOUR_OF_DAY));
        System.out.println("Минуты: " + cal.get(Calendar.MINUTE));
        System.out.println("Секунды: " + cal.get(Calendar.SECOND));
    }
}
