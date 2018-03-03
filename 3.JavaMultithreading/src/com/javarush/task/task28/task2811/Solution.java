package com.javarush.task.task28.task2811;

/* 
ReentrantReadWriteLock
*/

import java.util.LinkedHashMap;

public class Solution {
    public static void main(String[] args) {
        ReadWriteMap<Integer, String> linkedSafeMap = new ReadWriteMap<Integer, String>(new LinkedHashMap<>());
        linkedSafeMap.put(0, "good");
        linkedSafeMap.put(1, "best");

        System.out.println(linkedSafeMap.get(0));
        System.out.println(linkedSafeMap.get(1));
    }
}
