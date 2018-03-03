package com.javarush.task.task39.task3909;

/*
Одно изменение
*/
public class Solution {
    public static void main(String[] args) {
//        System.out.println(isOneEditAway("", ""));  // true
//        System.out.println(isOneEditAway("abcd", "dsaf"));  // true
//        System.out.println();
//        System.out.println(isOneEditAway("abcd", "abce"));  // true
//        System.out.println(isOneEditAway("abcd", "fbcd"));  // true
//        System.out.println(isOneEditAway("abcd", "arcd"));  // true
//        System.out.println(isOneEditAway("abcd", "adfe"));  // false
//        System.out.println();
//        System.out.println(isOneEditAway("abc", "abcd"));  // true
//        System.out.println(isOneEditAway("bcd", "abcd"));  // true
//        System.out.println(isOneEditAway("acd", "abcd"));  // true
//        System.out.println(isOneEditAway("abd", "abcd"));  // true
//        System.out.println(isOneEditAway("add", "abcd"));  // false
//        System.out.println();
//        System.out.println(isOneEditAway("abcd", "abc"));  // true
//        System.out.println(isOneEditAway("abcd", "bcd"));  // true
//        System.out.println(isOneEditAway("abcd", "acd"));  // true
//        System.out.println(isOneEditAway("abcd", "abd"));  // true
//        System.out.println(isOneEditAway("abcd", "add"));  // false
    }

    public static boolean isOneEditAway(String first, String second) {
        if (first == null || second == null) return false;
        if (first.isEmpty() && second.isEmpty()) return true;
        if (first.equals(second)) return true;

        int strategy = first.length() - second.length();
        switch (strategy) {
            case 0:
                return checkEdit(first, second);
            case -1:
                return checkAdd(first, second);
            case 1:
                return checkRemove(first, second);
            default:
                return false;
        }
    }

    private static boolean checkEdit(String first, String second) {
        int count = 0;
        for (int i = 0; i < first.length(); i++) {
            if (first.charAt(i) != second.charAt(i))
                count++;
        }
        return count == 1;
    }

    private static boolean checkAdd(String first, String second) {
        return checkRemove(second, first);
    }

    private static boolean checkRemove(String first, String second) {
        StringBuilder builder;
        for (int i = 0; i < first.length(); i++) {
            builder = new StringBuilder(first);
            builder.deleteCharAt(i);
            if (builder.toString().equals(second))
                return true;
        }
        return false;
    }
}
