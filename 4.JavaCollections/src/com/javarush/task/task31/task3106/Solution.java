package com.javarush.task.task31.task3106;

import java.io.*;
import java.util.*;
import java.util.zip.ZipInputStream;


/*
Разархивируем файл
*/
public class Solution {
    public static void main(String[] args) throws Exception{

        String resultFile = args[0];
        List<String> listFiles = new ArrayList<>();
        for (int i = 1; i < args.length; i++) {
            listFiles.add(args[1]);
        }

        Collections.sort(listFiles);
        List fileInputStreams = new ArrayList();

        for (String name: listFiles) {
            fileInputStreams.add(new FileInputStream(name));
        }

        SequenceInputStream sequenceInputStream = new SequenceInputStream(Collections.enumeration(fileInputStreams));
        ZipInputStream zipInputStream = new ZipInputStream(sequenceInputStream);
        OutputStream out = new FileOutputStream(new File(resultFile));

        byte[] buf = new byte[1024 * 1024];
        while (zipInputStream.getNextEntry() != null) {
            int b;
            while ((b = zipInputStream.read(buf)) != -1) {
                out.write(buf, 0, b);
            }
        }

        sequenceInputStream.close();
        zipInputStream.closeEntry();
        out.close();
    }
}
