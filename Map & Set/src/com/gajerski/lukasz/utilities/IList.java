package com.gajerski.lukasz.utilities;

import java.util.Iterator;

public interface IList<T> extends Iterable<T> {

    boolean add(T value);

    boolean add(int index, T value);

    void clear();

    T get(int index);

    T set(int index, T value);

    boolean isEmpty();

    Iterator<T> iterator();

    T delete(int index);

    boolean delete(T value);

    int size();
}
