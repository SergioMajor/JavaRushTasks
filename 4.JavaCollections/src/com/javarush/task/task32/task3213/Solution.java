package com.javarush.task.task32.task3213;

import java.io.BufferedReader;
import java.io.Reader;
import java.io.StringReader;

/* 
Шифр Цезаря
*/
public class Solution {
    public static void main(String[] args) throws Exception {
        StringReader reader = new StringReader("Khoor Dpljr");
        System.out.println(decode(reader, -3));  //Hello Amigo

    }

    public static String decode(StringReader reader, int key)  {
        key %= 27;

        try {
            String message = executor(reader);
            StringBuilder stringBuilder = new StringBuilder();

            for (Character character : message.toCharArray()) {
                stringBuilder.append((char) (character + key));
//                stringBuilder.append(decodeCharacter(character, key));
            }

            return stringBuilder.toString();
        } catch (Exception e) {
            return "";
        }
    }

//    private static char decodeCharacter(char character, int key) {
//        char newCharacter = (char) (Character.toLowerCase(character) + key);
//        int difference;
//
//        if (newCharacter > 'z') {
//            difference = getAddKey(newCharacter);
//            newCharacter = Character.isLowerCase(character) ? (char) ('a' + difference) : (char) ('A' + difference);
//
//        } else if (newCharacter < 'a') {
//            difference = getSubKey(newCharacter);
//            newCharacter = Character.isLowerCase(character) ? (char) ('z' - difference) : (char) ('Z' - difference);
//        } else if (Character.isUpperCase(character)){
//            newCharacter = Character.toUpperCase(newCharacter);
//        }
//
//        return newCharacter;
//    }

//    private static int getSubKey(Character character) {
//        return ('a' - character) - 1;
//    }

//    private static int getAddKey(Character character) {
//        return (character - 'z') - 1;
//    }

    private static String executor(Reader reader) throws Exception {
        BufferedReader br = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null)
            stringBuilder.append(line);

        return stringBuilder.toString();
    }
}
