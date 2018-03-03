package com.javarush.task.task31.task3105;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/* 
Добавление файла в архив
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        // Первый аргумент - полный путь к файлу fileName
        Path fileName = Paths.get(args[0]);
        // Второй аргумент - путь к zip-архиву
        Path zipPath = Paths.get(args[1]);

        Path tempZipFile = Files.createTempFile(null, null);

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(tempZipFile))) {
            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipPath))) {
                ZipEntry zipEntry = zipInputStream.getNextEntry();

                while (zipEntry != null) {
                    zipOutputStream.putNextEntry(new ZipEntry(zipEntry.getName()));
                    copyData(zipInputStream, zipOutputStream);

                    zipInputStream.closeEntry();
                    zipOutputStream.closeEntry();

                    zipEntry = zipInputStream.getNextEntry();
                }

                zipOutputStream.putNextEntry(new ZipEntry("new\\" + fileName.getFileName()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            Path file = Paths.get(String.valueOf(fileName));
            Files.copy(file, zipOutputStream);

            Files.move(tempZipFile, zipPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    private static void copyData(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[8 * 1024];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }
}
