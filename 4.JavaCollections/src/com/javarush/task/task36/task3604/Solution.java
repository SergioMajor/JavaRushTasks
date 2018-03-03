package com.javarush.task.task36.task3604;

/* 
Разбираемся в красно-черном дереве
*/
public class Solution {
    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();
        System.out.println(redBlackTree.isEmpty());
        redBlackTree.insert(2);
        redBlackTree.insert(3);
        redBlackTree.insert(4);
        redBlackTree.insert(1);
        redBlackTree.insert(0);
        redBlackTree.insert(7);
        redBlackTree.print();
        System.out.println(redBlackTree.isEmpty());
    }
}
