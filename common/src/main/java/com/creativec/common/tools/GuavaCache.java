package com.creativec.common.tools;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class GuavaCache {

    private static Cache localCache = CacheBuilder.newBuilder()
            .initialCapacity(50000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build();

    public static Object get(Integer key) {
        Object temp = null;
        try {
            temp = localCache.get(key, () -> new Object());
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return temp;

    }

    public static void put(Integer key, Object value) {
        localCache.put(key, value);
    }

    public static void clear() {
        localCache.invalidateAll();
    }
}
