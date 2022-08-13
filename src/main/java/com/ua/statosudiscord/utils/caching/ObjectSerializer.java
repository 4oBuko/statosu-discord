package com.ua.statosudiscord.utils.caching;

public interface ObjectSerializer<T> {
    void cacheObject(T object, String path);

    T getFromCache(String path);
}
