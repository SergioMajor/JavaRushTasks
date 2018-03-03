package com.javarush.task.task35.task3509;

import java.util.*;


/* 
Collections & Generics
*/
public class Solution {

    public static void main(String[] args) {
    }

    public static <T> ArrayList<T> newArrayList(T... elements) {
        if (elements != null) {
            ArrayList<T> arrayList = new ArrayList<>();
            for (T element : elements)
                arrayList.add(element);

            return arrayList;
        }

        return null;
    }

    public static <T> HashSet<T> newHashSet(T... elements) {
        if (elements != null) {
            HashSet<T> hashSet = new HashSet<>();
            for (T o : elements)
                hashSet.add(o);

            return hashSet;
        }

        return null;
    }

    public static <K,V> HashMap<K,V> newHashMap(List<? extends K> keys, List<? extends V> values) {
        //напишите тут ваш код
        HashMap<K, V> hashMap = new HashMap<>();
        if (keys.size() == values.size()) {
            for (int i = 0; i < keys.size(); i++)
                hashMap.put(keys.get(i), values.get(i));

            return hashMap;
        }
        throw new IllegalArgumentException();
    }
}
