package com.javarush.task.task37.task3708.retrievers;

import com.javarush.task.task37.task3708.cache.LRUCache;
import com.javarush.task.task37.task3708.storage.Storage;

public class CachingProxyRetriever implements Retriever{

    Storage storage;
    OriginalRetriever originalRetriever;
    LRUCache<Long, Object> lruCache = new LRUCache<>(16);

    public CachingProxyRetriever(Storage storage) {
        this.storage = storage;
        originalRetriever = new OriginalRetriever(storage);
    }

    @Override
    public Object retrieve(long id) {
        Object ob = lruCache.find(id);
        if (ob == null) {
            ob = originalRetriever.retrieve(id);
            lruCache.set(id, ob);
        }

        return ob;
    }
}
