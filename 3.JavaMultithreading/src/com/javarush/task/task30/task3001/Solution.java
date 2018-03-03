package com.javarush.task.task30.task3001;

import java.math.BigInteger;

/*
Конвертер систем счислений
*/
public class Solution {

    private final static String PATTERN_HEX = "-?[0-9a-fA-F]+";
    private final static String PATTERN_TW= "-?[0-9a-bA-B]+";
    private final static String PATTERN_START_NUMBER= "-?[0-";
    private final static String PATTERN_END_NUMBER= "]+";


    public static void main(String[] args)  {
        Number number = new Number(NumerationSystemType._10, "74");
        Number result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._3);
        System.out.println(result);
        number = new Number(NumerationSystemType._2, "1101001000000001100001001110110111111100110010101000100111011011011001001011001100011001100000111101111");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._4);
        System.out.println(result);
        number = new Number(NumerationSystemType._2, "1101001000000001100001001110110111111100110010101000100111011011011001001011001100011001100000111101111");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._5);
        System.out.println(result);
        number = new Number(NumerationSystemType._2, "1101001000000001100001001110110111111100110010101000100111011011011001001011001100011001100000111101111");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._6);
        System.out.println(result);
        number = new Number(NumerationSystemType._2, "1101001000000001100001001110110111111100110010101000100111011011011001001011001100011001100000111101111");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._7);
        System.out.println(result);
        number = new Number(NumerationSystemType._2, "1101001000000001100001001110110111111100110010101000100111011011011001001011001100011001100000111101111");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._8);
        System.out.println(result);
        number = new Number(NumerationSystemType._2, "1101001000000001100001001110110111111100110010101000100111011011011001001011001100011001100000111101111");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._9);
        System.out.println(result);
        number = new Number(NumerationSystemType._2, "1101001000000001100001001110110111111100110010101000100111011011011001001011001100011001100000111101111");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._10);
        System.out.println(result);
        number = new Number(NumerationSystemType._2, "1101001000000001100001001110110111111100110010101000100111011011011001001011001100011001100000111101111");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._12);
        System.out.println(result);
        number = new Number(NumerationSystemType._2, "1101001000000001100001001110110111111100110010101000100111011011011001001011001100011001100000111101111");
        result = convertNumberToOtherNumerationSystem(number, NumerationSystemType._16);
        System.out.println(result);
    }

    public static Number convertNumberToOtherNumerationSystem(Number number, NumerationSystem expectedNumerationSystem) {

        if (!isRightSystem(number))
            throw new NumberFormatException();

        BigInteger decimal = new BigInteger(number.getDigit(), number.getNumerationSystem().getNumerationSystemIntValue());

        return new Number(expectedNumerationSystem, decimal.toString(expectedNumerationSystem.getNumerationSystemIntValue()));
    }


    private static boolean isRightSystem(Number number) {
        String pattern = getPatternByNumerationSystem(number.getNumerationSystem());

        return number.getDigit().matches(pattern);
    }

    private static String getPatternByNumerationSystem(NumerationSystem numerationSystem) {
        if (numerationSystem.equals(NumerationSystemType._12))
            return PATTERN_TW;
        else if (numerationSystem.equals(NumerationSystemType._16))
            return PATTERN_HEX;
        else
            return PATTERN_START_NUMBER + (numerationSystem.getNumerationSystemIntValue() - 1) + PATTERN_END_NUMBER;
    }

}
