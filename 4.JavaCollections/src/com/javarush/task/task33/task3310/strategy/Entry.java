package com.javarush.task.task33.task3310.strategy;

import java.io.Serializable;
import java.util.Objects;

public class Entry implements Serializable{
    Long key;
    String value;
    int hash;
    Entry next;

    public Entry(int hash, Long key, String value, Entry next) {
        this.key = key;
        this.value = value;
        this.hash = hash;
        this.next = next;
    }

    public Long getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Entry getNext() {
        return next;
    }


    @Override
    public int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        Entry objEntry = (Entry) obj;
        return key.equals(objEntry.getKey()) && value.equals(objEntry.getValue());
    }


    @Override
    public String toString() {
        return key + "=" + value;
    }
}
