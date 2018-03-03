package com.javarush.task.task39.task3910;

/*
isPowerOfThree
*/
public class Solution {
    public static void main(String[] args) {
//        System.out.println(isPowerOfThree(3));
//        System.out.println(isPowerOfThree(9));
//        System.out.println(isPowerOfThree(12));
//        System.out.println(isPowerOfThree(27));
//        System.out.println(isPowerOfThree(30));
    }

    public static boolean isPowerOfThree(int n) {
        if (n == 0) return false;
        if (n == 1) return true;

        while (n > 1) {
            if (n % 3 != 0) return false;
            n /= 3;
        }
        return n == 1;
    }
}
