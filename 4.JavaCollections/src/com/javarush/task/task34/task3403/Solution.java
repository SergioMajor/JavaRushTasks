package com.javarush.task.task34.task3403;

/* 
Разложение на множители с помощью рекурсии
*/
public class Solution {
    public void recursion(int n) {
        if (n <= 1) {
            return;
        }

        int x = getDev(n, 2);
        int dev = n / x;
        System.out.print(x);

        if (dev != 0 && dev != 1) {
            System.out.print(" ");
            recursion(dev);
        }
    }

    private int getDev(int n, int x) {
        return n % x == 0 ? x : getDev(n, x + 1);
    }
}
