package com.javarush.task.task30.task3009;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/* 
Палиндром?
*/

public class Solution {

    private final static String PATTERN_DEC = "-?[0-9]+";

    public static void main(String[] args) {
        System.out.println(getRadix("112"));        //expected output: [3, 27, 13, 15]
        System.out.println(getRadix("123"));        //expected output: [6]
        System.out.println(getRadix("5321"));       //expected output: []
        System.out.println(getRadix("1A"));         //expected output: []
    }

    private static Set<Integer> getRadix(String number) {
        Set<Integer> numerationSystem = new HashSet<>();

        if (!isRightDecimal(number))
            return numerationSystem;

        BigInteger decimal;

        for (int i = 2; i < 37; i++) {
            decimal = new BigInteger(number, 10);

            if (isPalindrome(decimal.toString(i)))
                numerationSystem.add(i);
        }

        return numerationSystem;
    }

    private static boolean isPalindrome(String number) {

        int tmp = number.length() / 2;
        String first = number.substring(0, tmp);

        if (number.length() % 2 != 0)
            tmp++;

        StringBuilder second = new StringBuilder(number.substring(tmp));

        return first.equals(second.reverse().toString());
    }

    private static boolean isRightDecimal(String number) {
        return number.matches(PATTERN_DEC);
    }
}