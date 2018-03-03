package com.javarush.task.task39.task3908;


import java.util.HashMap;
import java.util.Map;

/*
Возможен ли палиндром?
*/
public class Solution {

    public static void main(String[] args)  {
//        System.out.println(isPalindromePermutation("aab"));
//        System.out.println(isPalindromePermutation("aabb"));
//        System.out.println(isPalindromePermutation("aabbv"));
//        System.out.println(isPalindromePermutation("aaab"));
    }

    public static boolean isPalindromePermutation(String s) {
        if (s == null || s.isEmpty()) return false;
        s = s.toLowerCase();
        Map<Character, Integer> map = new HashMap<>();
        for (Character c : s.toCharArray()) {
            if (map.containsKey(c)) map.put(c, map.get(c) + 1);
            else map.put(c, 1);
        }

        if (s.length() % 2 == 0) {
            for (Integer integer : map.values())
                if (integer % 2 != 0)
                    return false;
        } else {
            int count = 0;
            for (Integer integer : map.values()) {
                if (integer % 2 != 0) {
                    count++;
                }
            }
            if (count != 1) return false;
        }
        return true;
    }
}
