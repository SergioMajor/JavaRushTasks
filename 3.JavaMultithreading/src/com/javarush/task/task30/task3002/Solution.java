package com.javarush.task.task30.task3002;

/*
Осваиваем методы класса Integer
*/
public class Solution {
    private final static String PATTERN_HEX = "0x";
    private final static String PATTERN_EI = "0";
    private final static String PATTERN_BIN = "0b";

    public static void main(String[] args) {
        System.out.println(convertToDecimalSystem("0x16")); //22
        System.out.println(convertToDecimalSystem("012"));  //10
        System.out.println(convertToDecimalSystem("0b10")); //2
        System.out.println(convertToDecimalSystem("62"));   //62
    }

    public static String convertToDecimalSystem(String s) {
        String tmp = getNumberWithoutAttribute(s);
        int numerationSystem = getNumerationSystemByPattern(s);

        return String.valueOf(Integer.parseInt(tmp, numerationSystem));
    }

    private static int getNumerationSystemByPattern(String number) {
        if (number.startsWith(PATTERN_BIN))
            return 2;
        else if (number.startsWith(PATTERN_HEX))
            return 16;
        else if (number.startsWith(PATTERN_EI))
            return 8;

        return 10;
    }

    private static String getNumberWithoutAttribute(String number) {
        if (number.startsWith(PATTERN_BIN) || number.startsWith(PATTERN_HEX))
            return number.substring(2);
        else if (number.startsWith(PATTERN_EI))
            return number.substring(1);

        return number;
    }
}
