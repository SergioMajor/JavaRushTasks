package com.javarush.task.task36.task3613;

import java.util.concurrent.SynchronousQueue;

/*
Найти класс по описанию
*/
public class Solution {
    private static String pathConcurrent = "com.javarush";
    private final static String DIR_SEPARATOR = "/";
    private final static String PACK_SEPARATOR = ".";
    private final static String CLASS_END = ".class";

    public static void main(String[] args) {
        System.out.println(getExpectedClass());
    }

    public static Class getExpectedClass() {
        return SynchronousQueue.class;
    }
}