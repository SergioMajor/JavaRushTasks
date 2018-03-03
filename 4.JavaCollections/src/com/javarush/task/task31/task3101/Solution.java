package com.javarush.task.task31.task3101;

import java.io.*;
import java.util.*;

/*
Проход по дереву файлов
*/
public class Solution {
    private static Map<String, File> files = new TreeMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        File path = new File(args[0]);
        File resultFileAbsolutePath = new File(args[1]);
        File newFile = new File(resultFileAbsolutePath.getParent() + "\\" + "allFilesContent.txt");

        FileOutputStream out = new FileOutputStream(newFile);

        try {
            if (resultFileAbsolutePath.exists() && !newFile.exists())
                FileUtils.renameFile(resultFileAbsolutePath, newFile);
            listFilesForFolder(path);

            try {
                for (Map.Entry<String, File> iterator: files.entrySet()) {
                    FileInputStream in = new FileInputStream(iterator.getValue());
                    byte[] buffer = new byte[in.available()];

                    while (in.available() != 0) {
                        int length = in.read(buffer);
                        out.write(buffer, 0, length);
                    }

                    out.write("\n".getBytes());
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void listFilesForFolder(final File folder) {
        for (final File file : folder.listFiles()) {
            if (file.isDirectory()) {
                listFilesForFolder(file);
            } else {
                if (file.length() <= 50) {
                    files.put(file.getName(), file);
                }
            }
        }
    }
}
