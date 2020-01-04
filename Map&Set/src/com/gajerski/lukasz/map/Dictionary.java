package com.gajerski.lukasz.map;

import com.gajerski.lukasz.utilities.ArrayList;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Dictionary<E> implements Map<E> {
    private ArrayList<Element> list;
    private Comparator<String> keyComparator;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class Element {
        private String key;
        private E value;

        Element(String key, E value) {
            this.key = key;
            this.value = value;
        }

        E getValue() {
            return value;
        }

        String getKey() {
            return key;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    private class KeyIterator implements Iterator<String> {
        Iterator<Element> arrayIter = list.iterator();

        @Override
        public boolean hasNext() {
            return arrayIter.hasNext();
        }

        @Override
        public String next() {
            return arrayIter.next().getKey();
        }
    }

    private class ValueIterator implements Iterator<E> {
        Iterator<Element> arrayIter = list.iterator();

        @Override
        public boolean hasNext() {
            return arrayIter.hasNext();
        }

        @Override
        public E next() {

            return arrayIter.next().getValue();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Dictionary(Comparator<String> keyComparator) {
        this.list = new ArrayList<>();
        this.keyComparator = keyComparator;
    }

    @Override
    public E get(String key) throws NoSuchElementException {
        int pos = getIdOfKey(key);
        if (pos == -1) throw new NoSuchElementException();

        return list.get(pos).getValue();
    }

    @Override
    public boolean put(String key, E value) {
        for (int i = 0; i < list.size(); i++) {
            int compareValue = keyComparator.compare(list.get(i).getKey(), key);
            if (compareValue > 0) {
                list.add(i, new Element(key, value));
                return true;
            }

            if (compareValue == 0)
                return false;
        }

        list.add(new Element(key, value));
        return true;
    }

    @Override
    public boolean containsKey(String key) {
        return getIdOfKey(key) != -1;
    }

    @Override
    public E remove(String key) {
        int pos = getIdOfKey(key);
        if (pos == -1)
            return null;
        return list.delete(pos).getValue();
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

    private int getIdOfKey(String key) {
        return search(key, 0, list.size() - 1);
    }

    //binary search
    private int search(String key, int startIndex, int endIndex) {
        if (startIndex > endIndex) return -1;

        int index = (startIndex + endIndex) / 2;
        int compareResult = keyComparator.compare(key, list.get(index).getKey());

        if (compareResult < 0) return search(key, startIndex, index - 1);
        if (compareResult > 0) return search(key, index + 1, endIndex);
        return index;
    }

    @Override
    public String toString() {
        return list.toString();
    }

    public Iterator<String> keyIterator() {
        return new KeyIterator();
    }

    public Iterator<E> valueIterator() {
        return new ValueIterator();
    }

}
