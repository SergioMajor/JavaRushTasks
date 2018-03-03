package com.javarush.task.task33.task3310.strategy;

import java.util.HashMap;
import java.util.Map;

public class HashMapStorageStrategy implements StorageStrategy {
    private HashMap<Long, String> data = new HashMap<>();

    @Override
    public boolean containsKey(Long key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(String value) {
        return data.containsValue(value);
    }

    @Override
    public void put(Long id, String key) {
        data.put(id, key);
    }

    @Override
    public Long getKey(String value) {
        for (Map.Entry<Long, String> iterator : data.entrySet())
            if (value.equals(iterator.getValue()))
                return iterator.getKey();

        return null;
    }

    @Override
    public String getValue(Long key) {
        return data.get(key);
    }
}
