package com.javarush.task.task39.task3913;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Date;

public class Solution {
    public static void main(String[] args) throws IOException {
        LogParser logParser = new LogParser(Paths.get("C:/Users/SergeyMajor/IdeaProjects/JavaRushTasks/JavaRushTasks/4.JavaCollections/src/com/javarush/task/task39/task3913/logs/"));

        System.out.println(logParser.getNumberOfUniqueIPs(null, new Date())); // expect 3
        System.out.println(logParser.getIPsForUser("Amigo", null, null)); // expect [120.120.120.122, 12.12.12.12, 127.0.0.1]
        System.out.println(logParser.getIPsForEvent(Event.LOGIN, new Date(), null)); // expect [127.0.0.1]
        System.out.println(logParser.getIPsForEvent(Event.DONE_TASK, new Date(), null)); // [146.34.15.5]
        System.out.println(logParser.getIPsForStatus(Status.ERROR, null, null)); // [192.168.100.2]

        // get ip for user = "Eduard Petrovich Morozko" and date between "11.12.2013 0:00:00" and "03.01.2014 23:59:59"
        // expect [146.34.15.5, 127.0.0.1]

        System.out.println();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String query;
        System.out.println("Input: ");
        while (!(query = reader.readLine()).equals("ex")) {
            System.out.println(logParser.execute(query));
            System.out.println("Input: ");
        }
    }
}