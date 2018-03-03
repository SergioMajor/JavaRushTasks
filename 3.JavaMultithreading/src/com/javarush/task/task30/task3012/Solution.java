package com.javarush.task.task30.task3012;

/* 
Получи заданное число
*/

public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.createExpression(1234);
    }

    public void createExpression(int number) {
        String ternaryNumber = convertToTernary(number);
        String charTernary = convertToCharTernary(ternaryNumber);
        String result = getExpressionByTernaryNumber(number, charTernary);

        System.out.println(result);
    }

    private String convertToTernary(int number) {
        int reduction = number % 3;

        if (number > 2) {
            int newNumber = number / 3;

            if (reduction == 2)
                newNumber++;

            return convertToTernary(newNumber) + (reduction);
        }

        return String.valueOf(number);
    }

    private String convertToCharTernary(String number) {
        StringBuilder result = new StringBuilder();

        switch (number.charAt(0)) {
            case '2':
                result.append('+').append('-');
                break;
            case '1':
                result.append('+');
                break;
            default:
                result.append('0');
                break;
        }

        for (int i = 1; i < number.length(); i++) {
            switch (number.charAt(i)) {
                case '2':
                    result.append('-');
                    break;
                case '1':
                    result.append('+');
                    break;
                default:
                    result.append('0');
                    break;
            }
        }

        return result.toString();
    }

    private String getExpressionByTernaryNumber(int initial, String ternary) {
        StringBuilder result = new StringBuilder(initial + " =");
        ternary = new StringBuilder(ternary).reverse().toString();

        int step = 0;
        for (Character character: ternary.toCharArray()) {
            if (character != '0') {
                result.append(" ").append(character).append(" ").append((int) Math.pow(3, step));
            }

            step++;
        }

        return result.toString();
    }
}