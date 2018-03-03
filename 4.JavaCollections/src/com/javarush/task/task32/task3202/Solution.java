package com.javarush.task.task32.task3202;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/* 
Читаем из потока
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        StringWriter writer = getAllDataFromInputStream(new FileInputStream("testFile.log"));
        System.out.println(writer.toString());
    }

    public static StringWriter getAllDataFromInputStream(InputStream is) {
        try {
            byte[] buffer = new byte[is.available()];
            is.read(buffer, 0, is.available());

            StringWriter result = new StringWriter();
            result.write(new String(buffer));

            return result;
        } catch (Exception e) {
            return new StringWriter();
        }
    }
}