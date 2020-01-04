package com.gajerski.lukasz.sets;

public interface Set<E> {
    boolean add(E value);

    boolean contains(E value);

    void remove(E value);

    void clear();

    int size();

    boolean isEmpty();
}
