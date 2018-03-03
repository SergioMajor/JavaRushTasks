package com.javarush.task.task36.task3610;

import java.io.Serializable;
import java.util.*;

public class MyMultiMap<K, V> extends HashMap<K, V> implements Cloneable, Serializable {
    static final long serialVersionUID = 123456789L;
    private HashMap<K, List<V>> map;
    private int repeatCount;

    public MyMultiMap(int repeatCount) {
        this.repeatCount = repeatCount;
        map = new HashMap<>();
    }

    @Override
    public int size() {
        return values().size();
    }

    @Override
    public V put(K key, V value) {
        List<V> list = map.get(key);

        if (list == null) {
            List<V> newList = new ArrayList<>();
            newList.add(value);
            map.put(key, newList);
            return null;
        } else if (list.size() < repeatCount) {
            list.add(value);
            return list.get(list.size() - 2);
        } else {
            list.remove(0);
            list.add(value);
            return list.get(list.size() - 2);
        }
    }

    @Override
    public V remove(Object key) {
        if (!containsKey(key)) return null;

        if (map.get(key).size() > 0) {
            V removed = map.get(key).remove(0);

            if (map.get(key).size() == 0) map.remove(key);
            return removed;
        } else {
            map.remove(key);
            return null;
        }
    }


    @Override
    public Set<K> keySet() {
        return map.keySet();
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> result = new ArrayList<>();
        for (List<V> list : map.values()) result.addAll(list);

        return result;
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, List<V>> entry : map.entrySet()) {
            sb.append(entry.getKey());
            sb.append("=");
            for (V v : entry.getValue()) {
                sb.append(v);
                sb.append(", ");
            }
        }
        String substring = sb.substring(0, sb.length() - 2);
        return substring + "}";
    }
}