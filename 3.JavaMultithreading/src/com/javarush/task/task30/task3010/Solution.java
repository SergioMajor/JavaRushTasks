package com.javarush.task.task30.task3010;

/* 
Минимальное допустимое основание системы счисления
*/
public class Solution {

    private final static java.lang.String PATTERN_NUMERATION_SYSTEM = "-?[0-9a-zA-Z]+";

    public static void main(String[] args) {

        try {
            if (!isRight(args[0])) {
                System.out.println("incorrect");

            } else {
                System.out.println(getNumerationSystem(args[0]));
            }
        } catch (Exception e) {
        }
    }
    private static char getMax(String number) {
        number = number.toLowerCase();
        char max = number.charAt(0);

        for (Character character: number.toCharArray()) {
            if (max < character)
                max = character;
        }

        return max;
    }

    private static int getNumerationSystem(String number) {
        char maxChar = getMax(number);

        if (Character.isDigit(maxChar)) {
            int tmp = Integer.parseInt(String.valueOf(maxChar));

            return tmp == 0 || tmp == 1 ? 2 : tmp + 1;
        } else {
            return 11 + maxChar - 'a';
        }
    }

    private static boolean isRight(String number) {
        return number.matches(PATTERN_NUMERATION_SYSTEM);
    }
}