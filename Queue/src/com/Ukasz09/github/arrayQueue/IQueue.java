package com.Ukasz09.github.arrayQueue;

public interface IQueue<T> {
    class EmptyQueueException extends Exception {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class FullQueueException extends Exception {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    boolean isEmpty();

    boolean isFull();

    T dequeue() throws EmptyQueueException;

    void enqueue(T elem) throws FullQueueException;

    int size();

    T first() throws EmptyQueueException;

}
