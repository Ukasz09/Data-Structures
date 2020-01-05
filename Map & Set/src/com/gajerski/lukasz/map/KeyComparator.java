package com.gajerski.lukasz.map;

import java.util.Comparator;

public class KeyComparator<T extends String > implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        return o1.compareTo(o2);
    }
}
