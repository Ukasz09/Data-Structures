package com.Ukasz09.github.arrayQueue;

public class ArrayQueue<T> implements IQueue<T> {
    private static final int DEFAULT_CAPACITY = 20;
    private T[] array;
    private int beginIndex;
    private int endIndex;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @SuppressWarnings("unchecked")
    public ArrayQueue(int size) {
        if (size > 0)
            array = (T[]) new Object[size + 1];
        else array = (T[]) new Object[DEFAULT_CAPACITY + 1];
    }

    public ArrayQueue() {
        this(DEFAULT_CAPACITY);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean isEmpty() {
        return beginIndex == endIndex;
    }

    @Override
    public boolean isFull() {
        return beginIndex == (endIndex + 1) % array.length;
    }

    @Override
    public T dequeue() throws EmptyQueueException {
        if (isEmpty())
            throw new EmptyQueueException();
        T retValue = array[beginIndex++];
        beginIndex %= array.length;
        return retValue;
    }

    @Override
    public void enqueue(T elem) throws FullQueueException {
        if (isFull())
            throw new FullQueueException();
        array[endIndex++] = elem;
        endIndex %= array.length;
    }

    @Override
    public int size() {
        return (endIndex + array.length - beginIndex) % array.length;
    }

    @Override
    public T first() throws EmptyQueueException {
        if (isEmpty())
            throw new EmptyQueueException();
        return array[beginIndex];
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append('[');
        if (!isEmpty()) {
            int actualIndex = beginIndex;
            while (beginIndex != (actualIndex + 1) % array.length && array[actualIndex] != null) {
                buffer.append(array[actualIndex]).append(", ");
                actualIndex++;
                actualIndex %= array.length;
            }
            buffer.setLength(buffer.length() - 2);
        }
        buffer.append(']');
        return buffer.toString();
    }
}

