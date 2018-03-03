package com.javarush.task.task37.task3714;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;

/* 
Древний Рим
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input a roman number to be converted to decimal: ");
        String romanString = bufferedReader.readLine();
        System.out.println("Conversion result equals " + romanToInteger(romanString));
    }

    public static int romanToInteger(String s) {
        s = new StringBuilder(s.toUpperCase()).reverse().toString();

        int decimal = 0;
        int lastNumber = 0;

        for (Character c : s.toCharArray()) {
            switch (c) {
                case 'M':
                    decimal = processDecimal(1000, lastNumber, decimal);
                    lastNumber = 1000;
                    break;
                case 'D':
                    decimal = processDecimal(500, lastNumber, decimal);
                    lastNumber = 500;
                    break;
                case 'C':
                    decimal = processDecimal(100, lastNumber, decimal);
                    lastNumber = 100;
                    break;
                case 'L':
                    decimal = processDecimal(50, lastNumber, decimal);
                    lastNumber = 50;
                    break;
                case 'X':
                    decimal = processDecimal(10, lastNumber, decimal);
                    lastNumber = 10;
                    break;
                case 'V':
                    decimal = processDecimal(5, lastNumber, decimal);
                    lastNumber = 5;
                    break;
                case 'I':
                    decimal = processDecimal(1, lastNumber, decimal);
                    lastNumber = 1;
                    break;
            }
        }

        return decimal;
    }

    private static int processDecimal(int decimal, int lastNumber, int lastDecimal) {
        return lastNumber > decimal ? lastDecimal - decimal : lastDecimal + decimal;
    }
}
