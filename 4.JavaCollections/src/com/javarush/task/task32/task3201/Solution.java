package com.javarush.task.task32.task3201;

import java.io.RandomAccessFile;

/*
Запись в существующий файл
*/
public class Solution {
    public static void main(String... args) {
        try (RandomAccessFile accessFile = new RandomAccessFile(args[0], "rw")) {
            int pointer = Integer.parseInt(args[1]);

            if (pointer >= 0 && pointer < accessFile.length())
                accessFile.seek(pointer);
            else if (pointer > 0)
                accessFile.seek(accessFile.length());

            accessFile.write(args[2].getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
