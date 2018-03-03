package com.javarush.task.task39.task3904;

/* 
Лестница
*/
public class Solution {
    private static int n = 70;

    public static void main(String[] args) {
        System.out.println("Number of possible runups for " + n + " stairs is: " + countPossibleRunups(n));
    }

    public static long countPossibleRunups(int n) {
        if (n < 0) return 0L;
        if (n == 0) return 1L;

        long[] map = new long[n];
        for (int i = 0; i < n; i++) map[i] = -1;
        return countPossibleRunups(n, map);
    }

    private static long countPossibleRunups(int n, long[] map) {
        if (n < 0) return 0L;
        else if (n == 0) return 1L;
        else if (map[n-1] > -1) return map[n-1];
        else {
            map[n - 1] = (countPossibleRunups(n - 1, map) + countPossibleRunups(n - 2, map) + countPossibleRunups(n - 3, map));
            return map[n - 1];
        }
    }
}

