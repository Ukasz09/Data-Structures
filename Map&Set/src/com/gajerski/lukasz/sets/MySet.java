package com.gajerski.lukasz.sets;

import com.gajerski.lukasz.utilities.ArrayList;

import java.util.NoSuchElementException;

public class MySet<E> implements Set<E> {
    private ArrayList<E> list;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public MySet() {
        this.list = new ArrayList<>();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean add(E value) {
        if (!contains(value)) {
            list.add(value);
            return true;
        }
        return false;
    }

    @Override
    public boolean contains(E value) {
        return list.indexOf(value) != -1;
    }

    @Override
    public void remove(E value) throws NoSuchElementException {
        int index = list.indexOf(value);
        if (index == -1)
            throw new NoSuchElementException();
        list.delete(index);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
