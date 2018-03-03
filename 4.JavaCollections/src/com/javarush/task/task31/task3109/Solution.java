package com.javarush.task.task31.task3109;

import java.io.*;
import java.util.Properties;

/* 
Читаем конфиги
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        Solution solution = new Solution();
        Properties properties = solution.getProperties("4.JavaCollections/src/com/javarush/task/task31/task3109/properties.xml");
        properties.list(System.out);

        properties = solution.getProperties("4.JavaCollections/src/com/javarush/task/task31/task3109/properties.txt");
        properties.list(System.out);

        properties = solution.getProperties("4.JavaCollections/src/com/javarush/task/task31/task3109/notExists");
        properties.list(System.out);
    }

    public Properties getProperties(String fileName) throws FileNotFoundException {
        Properties properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(fileName));

            if (fileName.endsWith(".xml")) {
                properties.loadFromXML(fileInputStream);
            } else {
                properties.load(fileInputStream);
            }
            return properties;
        } catch (IOException e) {
            return properties;
        }

    }
}
