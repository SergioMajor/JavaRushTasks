package com.javarush.task.task40.task4008;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Locale;

/* 
Работа с Java 8 DateTime API
*/

public class Solution {
    public static void main(String[] args) {
        printDate("9.10.2017 5:56:45");
        System.out.println();
        printDate("21.4.2014");
        System.out.println();
        printDate("17:33:40");
    }

    public static void printDate(String date) {
        if (date == null) return;

        DateTimeFormatter formatter_1 = DateTimeFormatter.ofPattern("d.M.yyyy", Locale.ENGLISH);
        DateTimeFormatter formatter_2 = DateTimeFormatter.ofPattern("H:m:s");

        LocalDate localDate;
        LocalTime localTime;

        try {
            if (date.contains(" ")) {
                localDate = LocalDate.parse(date.split(" ")[0], formatter_1);
                localTime = LocalTime.parse(date.split(" ")[1], formatter_2);
                printDate(localDate);
                printTime(localTime);
            } else if (date.contains(":")) {
                localTime = LocalTime.parse(date, formatter_2);
                printTime(localTime);
            } else {
                localDate = LocalDate.parse(date, formatter_1);
                printDate(localDate);
            }
        } catch (DateTimeParseException ignore) {}
    }

    private static void printDate(LocalDate localDate) {
        System.out.println("День: " + localDate.getDayOfMonth());
        System.out.println("День недели: " + localDate.getDayOfWeek().getValue());
        System.out.println("День месяца: " + localDate.getDayOfMonth());
        System.out.println("День года: " + localDate.getDayOfYear());
        System.out.println("Неделя месяца: " + localDate.get(WeekFields.of(Locale.getDefault()).weekOfMonth()));
        System.out.println("Неделя года: " + localDate.get(WeekFields.of(Locale.getDefault()).weekOfYear()));
        System.out.println("Месяц: " + localDate.getMonthValue());
        System.out.println("Год: " + localDate.getYear());
    }

    private static void printTime(LocalTime localTime) {
        System.out.println("AM или PM: " + (localTime.get(ChronoField.AMPM_OF_DAY) == 0 ? "AM" : "PM"));
        System.out.println("Часы: " + localTime.get(ChronoField.HOUR_OF_AMPM));
        System.out.println("Часы дня: " + localTime.getHour());
        System.out.println("Минуты: " + localTime.getMinute());
        System.out.println("Секунды: " + localTime.getSecond());
    }
}
