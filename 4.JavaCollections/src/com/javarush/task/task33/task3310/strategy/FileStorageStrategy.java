package com.javarush.task.task33.task3310.strategy;

public class FileStorageStrategy implements StorageStrategy {

    private final static int DEFAULT_INITIAL_CAPACITY = 16;
    private final static long DEFAULT_BUCKET_SIZE_LIMIT = 10_000L;

    private FileBucket[] table = new FileBucket[DEFAULT_INITIAL_CAPACITY];
    private int size;
    private long bucketSizeLimit = DEFAULT_BUCKET_SIZE_LIMIT;
    private long maxBucketSize = DEFAULT_BUCKET_SIZE_LIMIT;


    public long getBucketSizeLimit() {
        return bucketSizeLimit;
    }

    public void setBucketSizeLimit(long bucketSizeLimit) {
        this.bucketSizeLimit = bucketSizeLimit;
    }

    @Override
    public boolean containsKey(Long key) {
        return getEntry(key) != null;
    }

    @Override
    public boolean containsValue(String value) {
        if (value == null)
            return false;
        for (FileBucket table : table) {
            if (table == null) continue;
            Entry e = table.getEntry();
            while (e != null) {
                if (value.equals(e.value))
                    return true;
                e = e.next;
            }
        }
        return false;
    }

    @Override
    public void put(Long key, String value) {
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        if (table[index] != null) {
            Entry entry = table[index].getEntry();
            while (entry != null) {
                if (entry.getKey().equals(key)) {
                    entry.value = value;
                    return;
                }
                entry = entry.next;
            }
            addEntry(hash, key, value, index);
        } else {
            createEntry(hash, key, value, index);
        }
    }

    @Override
    public Long getKey(String value) {
        if (value == null) return 0L;

        for (FileBucket table : table) {
            if (table == null) continue;
            Entry e = table.getEntry();
            while (e != null) {
                if (value.equals(e.value))
                    return e.key;
                e = e.next;
            }
        }

        return 0L;
    }

    @Override
    public String getValue(Long key) {
        Entry e = getEntry(key);
        return null == e ? null : e.getValue();
    }

    public int hash(Long k) {
        return k.hashCode();
    }

    public int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    public Entry getEntry(Long key) {
        if (size == 0) return null;

        int hash = (key == null) ? 0 : hash(key);
        FileBucket fb = table[indexFor(hash, table.length)];
        if (fb == null) return null;

        Entry e = fb.getEntry();
        while (e != null) {
            if (e.key.equals(key)) return e;
            e = e.next;
        }

        return null;
    }

    public void resize(int newCapacity) {
        FileBucket[] fb = new FileBucket[newCapacity];
        transfer(fb);
        table = fb;
    }

    public void transfer(FileBucket[] newTable) {
        int newCapacity = newTable.length;
        for (FileBucket fb : table) {
            if (fb == null) continue;
            Entry e = fb.getEntry();
            while (null != e) {
                Entry next = e.next;
                int i = indexFor(e.hash, newCapacity);
                if (newTable[i] == null) {
                    e.next = null;
                    newTable[i] = new FileBucket();
                } else {
                    e.next = fb.getEntry();
                }
                newTable[i].putEntry(e);
                e = next;
            }
            fb.remove();
        }
    }

    public void addEntry(int hash, Long key, String value, int bucketIndex) {
        Entry e = table[bucketIndex].getEntry();
        table[bucketIndex].putEntry(new Entry(hash, key, value, e));
        size++;

        if (table[bucketIndex].getFileSize() > maxBucketSize)
            resize(2 * table.length);
    }

    public void createEntry(int hash, Long key, String value, int bucketIndex) {
        table[bucketIndex] = new FileBucket();
        table[bucketIndex].putEntry(new Entry(hash, key, value, null));
        size++;
    }
}