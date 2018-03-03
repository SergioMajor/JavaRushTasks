package com.javarush.task.task32.task3204;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;

/* 
Генератор паролей
*/
public class Solution {
//    private final static String PATTERN_PASSWORD = "^[a-zA-Z0-9]{8}";

    public static void main(String[] args) throws IOException {
        ByteArrayOutputStream password = getPassword();
        System.out.println(password.toString());
    }

    public static ByteArrayOutputStream getPassword() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StringBuilder builder = new StringBuilder("");

        while (!isValidPassword(builder.toString())) {
            builder = new StringBuilder("");
            for (int i = 0; i < 8; i++) {
                builder.append(getRandomCharacter());
            }
        }

        baos.write(builder.toString().getBytes());

        return baos;
    }

    private static char getRandomCharacter() {
        char low = 0;
        char high = 0;

        int key = new Random().nextInt(3);

        switch (key) {
            case 0:
                low = '0';
                high = '9';
                break;
            case 1:
                low = 'A';
                high = 'Z';
                break;
            case 2:
                low = 'a';
                high = 'z';
                break;
        }

        return (char) getRandomBetween(low, high);
    }

    private static int getRandomBetween(int low, int high) {

        return new Random().nextInt(high - low) + low;
    }

    private static boolean isValidPassword(String password) {
        boolean existNumber = false;
        boolean existCharLow = false;
        boolean existCharUp = false;

        for (Character character: password.toCharArray()) {
            if (Character.isDigit(character))
                existNumber = true;
            else if (Character.isUpperCase(character))
                existCharUp = true;
            else if (Character.isLowerCase(character))
                existCharLow = true;
        }

        return existNumber && existCharUp && existCharLow;
    }
}