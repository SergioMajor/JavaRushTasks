package com.javarush.task.task40.task4009;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.Locale;

/* 
Buon Compleanno!
*/

public class Solution {
    public static void main(String[] args) {
        System.out.println(weekDayOfBirthday("1.12.2015", "2016"));
    }

    public static String weekDayOfBirthday(String birthday, String year) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.M.yyyy");
        String d = birthday.substring(0, birthday.lastIndexOf("."))  + "." + year;
        LocalDate localDate = LocalDate.parse(d, formatter);
        Year y  = Year.parse(year);

        return localDate.withYear(y.get(ChronoField.YEAR)).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ITALIAN);
    }
}
