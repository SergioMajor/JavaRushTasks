package com.javarush.task.task37.task3707;

import java.io.*;
import java.util.*;

public class AmigoSet<E> extends AbstractSet<E> implements Serializable, Cloneable, Set<E> {

    private static final Object PRESENT = new Object();
    private transient HashMap<E, Object> map;

    public AmigoSet() {
        map = new HashMap<>();
    }

    public AmigoSet(Collection<? extends E> collection) {
        int capacity = (int) Math.max(16, Math.ceil(collection.size() / .75f));
        map = new HashMap<>(capacity);

        addAll(collection);
    }

    @Override
    public Object[] toArray() {
        return this.map.keySet().toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.map.keySet().toArray(a);
    }

    @Override
    public boolean add(E e) {
        try {
            Object ob = map.put(e, PRESENT);
            if (ob == null)
                return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e: c) { if (!add(e)) return false; }

        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        for (E e : this) {
            if (e.equals(o)) return true;
        }

        return false;
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public boolean remove(Object o) {
        try {
            Object ob = map.remove(o);
            if (ob != null) return true;
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    public Object clone() {
        try {
            AmigoSet<E> copy = (AmigoSet<E>) super.clone();
            copy.map = (HashMap<E, Object>) map.clone();
            return copy;
        } catch (Exception e) {
            throw new InternalError();
        }
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeFloat(HashMapReflectionHelper.callHiddenMethod(map, "loadFactor"));
        oos.writeInt(HashMapReflectionHelper.callHiddenMethod(map, "capacity"));
        oos.writeInt(size());

        for (Iterator i = map.keySet().iterator(); i.hasNext(); ) oos.writeObject(i.next());
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        float loadFactor = ois.readFloat();
        int capacity = ois.readInt();
        map = new HashMap<>(capacity, loadFactor);

        int size = ois.readInt();
        for (int i=0; i<size; i++) {
           add((E) ois.readObject());
        }
    }
}
