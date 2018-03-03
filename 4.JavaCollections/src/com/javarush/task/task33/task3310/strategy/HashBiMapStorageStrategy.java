package com.javarush.task.task33.task3310.strategy;

import com.google.common.collect.HashBiMap;

public class HashBiMapStorageStrategy implements StorageStrategy{

    private HashBiMap<Long, String> data = HashBiMap.create();

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
        return value == null ? Long.valueOf(0L) : data.inverse().get(value);
    }

    @Override
    public String getValue(Long key) {
        return data.get(key);
    }
}
