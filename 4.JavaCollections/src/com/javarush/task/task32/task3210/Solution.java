package com.javarush.task.task32.task3210;

import java.io.RandomAccessFile;

/* 
Используем RandomAccessFile
*/
public class Solution {
    public static void main(String... args) {
        try (RandomAccessFile raf = new RandomAccessFile(args[0], "rw")) {

            String text = args[2];
            int start = Integer.parseInt(args[1]);
            if (start > 0 && start < raf.length()) {
                raf.seek(start);
            }

            byte[] buffer = new byte[text.length()];
            raf.read(buffer, 0, text.length());

            String result = String.valueOf((new String(buffer).equals(text)));
            raf.seek(raf.length());
            raf.write(result.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
