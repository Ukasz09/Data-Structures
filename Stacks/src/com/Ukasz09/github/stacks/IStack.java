package com.Ukasz09.github.stacks;


public interface IStack<T> {

    class EmptyStackException extends Exception {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    class FullStackException extends Exception {
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    boolean isEmpty();

    boolean isFull();

    T pop() throws EmptyStackException;

    void push(T elem) throws FullStackException;

    int size();

    T top() throws EmptyStackException;
}
