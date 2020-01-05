package com.gajerski.lukasz.utilities;

import java.util.Iterator;
import java.util.RandomAccess;

public class ArrayList<T> extends AbstractList<T> implements RandomAccess {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private final int initialCapacity;
    private T[] array;
    private int size;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    public ArrayList(int capacity) {
        if (capacity <= 0)
            capacity = DEFAULT_INITIAL_CAPACITY;
        initialCapacity = capacity;
        array = (T[]) (new Object[capacity]);
        size = 0;
    }

    public ArrayList() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public ArrayList(ArrayList<T> arr) {
        array = (T[]) (new Object[arr.size()]);
        if (arr.size() >= 0) System.arraycopy(arr.getArray(), 0, array, 0, arr.size());
        size = array.length;
        initialCapacity = size;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    private void ensureCapacity(int capacity) {
        if (array.length < capacity) {
            T[] copy = (T[]) (new Object[capacity + capacity / 2]);
            System.arraycopy(array, 0, copy, 0, size);
            array = copy;
        }
    }

    private void checkOutOfBounds(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        array = (T[]) (new Object[initialCapacity]);
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean add(T value) {
        ensureCapacity(size + 1);
        array[size] = value;
        size++;
        return true;
    }

    @Override
    public boolean add(int index, T value) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        ensureCapacity(size + 1);
        if (index != size)
            System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = value;
        size++;
        return false;
    }

    @Override
    public T get(int index) {
        checkOutOfBounds(index);
        return array[index];
    }

    @Override
    public T set(int index, T element) {
        checkOutOfBounds(index);
        T retValue = array[index];
        array[index] = element;
        return retValue;
    }

    @Override
    public T delete(int index) {
        checkOutOfBounds(index);
        T retValue = array[index];
        int copyFrom = index + 1;
        if (copyFrom < size)
            System.arraycopy(array, copyFrom, array, index, size - copyFrom);
        --size;
        return retValue;
    }

    @Override
    public boolean delete(T value) {
        int pos = 0;
        while (pos < size && !array[pos].equals(value))
            pos++;
        if (pos < size) {
            delete(pos);
            return true;
        }
        return false;
    }

    private class InnerIterator implements Iterator<T> {
        int _pos = 0;

        @Override
        public boolean hasNext() {
            return _pos < size;
        }

        @Override
        public T next() {
            return array[_pos++];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new InnerIterator();
    }

    private T[] getArray() {
        return array;
    }

    public int indexOf(T value) {
        for (int i = 0; i < array.length; i++)
            if (array[i].equals(value))
                return i;
        return -1;
    }

}
