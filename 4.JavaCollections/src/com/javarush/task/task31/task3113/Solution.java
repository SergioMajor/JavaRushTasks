package com.javarush.task.task31.task3113;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
Что внутри папки?
*/
public class Solution {

    private static long countDir = 0;
    private static long countFile = 0;
    private static long space = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Path path = Paths.get(reader.readLine());

        if (Files.isDirectory(path)) {
            initAllInformation(path);

            System.out.println("Всего папок - " + countDir);
            System.out.println("Всего файлов - " + countFile);
            System.out.println("Общий размер - " + space);
        } else {
            System.out.println(path + " - не папка");
        }

    }

    private static void initAllInformation(Path path) throws IOException {
        for(Path file: Files.newDirectoryStream(path)) {
            if (Files.isDirectory(file)) {
                countDir++;
                initAllInformation(file);
            } else {
                countFile++;
                space += Files.size(file);
            }
        }
    }
}
