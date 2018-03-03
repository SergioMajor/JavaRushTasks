package com.javarush.task.task20.task2028;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/*
Построй дерево(1)
*/
public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {

    Entry<String> root = new Entry<>("0");

    public static void main(String[] args) {
        List<String> list = new CustomTree();
        for (int i = 1; i < 150; i++) {
            list.add(String.valueOf(i));
        }

        System.out.println(list.size());
//        System.out.println("Expected 3, actual is " + ((CustomTree) list).getParent("8"));
        list.remove("5");

//        System.out.println("Expected null, actual is " + ((CustomTree) list).getParent("11"));
    }


    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }


    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        Queue<Entry<String>> queue = new ConcurrentLinkedQueue<>();
        int size = 0;
        queue.add(root);

        while (!queue.isEmpty()) {
            Entry<String> current = queue.remove();

            if (current.leftChild != null) {
                queue.add(current.leftChild);
                size++;
            }

            if (current.rightChild != null) {
                queue.add(current.rightChild);
                size++;
            }
        }

        return size;
    }

    @Override
    public boolean add(String s) {
        Entry<String> newElement = new Entry<>(s);
        Queue<Entry<String>> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);

        while (true) {
            Entry<String> current = queue.remove();

            if (current.isAvailableToAddChildren()) {
                addElement(current, newElement);
                break;
            } else {
                if (current.leftChild != null)
                    queue.add(current.leftChild);

                if (current.rightChild != null)
                    queue.add(current.rightChild);
            }
        }

        return true;
    }

    private void addElement(Entry<String> current, Entry<String> newElement) {
        if (current.leftChild == null) {
            newElement.lineNumber = current.lineNumber + 1;
            current.leftChild = newElement;

        } else if (current.rightChild == null) {
            newElement.lineNumber = current.lineNumber + 1;
            current.rightChild = newElement;
        }

        newElement.parent = current;
        current.checkChildren();

    }

    @Override
    public boolean remove(Object o) {
        String removeItemName = (String) o;
        Entry<String> removeElement = findElement(removeItemName);

        if (removeElement != null && removeElement.parent != null) {
            if (removeElement.parent.leftChild.elementName.equals(removeElement.elementName))
                removeElement.parent.leftChild = null;
            else
                removeElement.parent.rightChild = null;

            removeElement.parent = null;
            return true;
        }

        return false;
    }

    public Entry<String> findElement(String removeItem) {

        Queue<Entry<String>> queue = new ConcurrentLinkedQueue<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Entry<String> current =  queue.remove();

            if (current.elementName.equals(removeItem)) {
                return current;
            } else {
                if (current.leftChild != null)
                    queue.add(current.leftChild);

                if (current.rightChild != null)
                    queue.add(current.rightChild);
            }
        }

        return null;
    }

    public String getParent(String s) {
        Entry<String> parent = findElement(s);
        if (parent != null)
            return parent.parent.elementName;

        return null;
    }


    static class Entry<T> implements Serializable {
        String elementName;
        int lineNumber;
        boolean availableToAddLeftChildren,
                availableToAddRightChildren;

        Entry<T> parent,
                leftChild,
                rightChild;

        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;
        }

        void checkChildren() {
            if (leftChild != null)
                availableToAddLeftChildren = false;

            if (rightChild != null)
                availableToAddRightChildren = false;
        }

        boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
}
