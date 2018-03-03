package com.javarush.task.task36.task3605;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

/* 
Использование TreeSet
*/
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(args[0]));
        TreeSet<Character> treeSet = new TreeSet<>();

        while (reader.ready()) {
            char chr = (char) reader.read();
            if (Character.isLetter(chr))
                treeSet.add(Character.toLowerCase(chr));
        }

        print(treeSet, treeSet.size() < 5 ? treeSet.size() : 5);
        reader.close();
    }

    private static void print(TreeSet<Character> treeSet, int length) {
        Iterator<Character> iterator = treeSet.iterator();
        while (iterator.hasNext() && length > 0) {
            System.out.print(iterator.next());
            length--;
        }
        System.out.println();
    }
}