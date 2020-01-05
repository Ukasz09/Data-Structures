package com.gajerski.lukasz.map;

public interface Map<E> {

    E get(String key);

    boolean put(String key, E value);

    boolean containsKey(String key);

    E remove(String key);

    void clear();

    int size();

    boolean isEmpty();
}
