package com.javarush.task.task31.task3102;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/* 
Находим все файлы
*/
public class Solution {
    public static List<String> getFileTree(String root) throws IOException {
        List<String> files = new ArrayList<>();

        Queue<File> queue = new PriorityQueue<>();
        queue.offer(new File(root));

        while(!queue.isEmpty()) {
            File child = queue.poll();
            if (child.isDirectory()) {
                for(File f : child.listFiles())
                    queue.offer(f);
            } else if (child.isFile()) {
                files.add(child.getAbsolutePath());
            }
        }

        return files;
    }

    public static void main(String[] args) {
        try {
            for (String s: getFileTree("D:\\hello"))
                System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
