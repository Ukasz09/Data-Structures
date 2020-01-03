package com.Ukasz09.github.stacks;

import com.Ukasz09.github.utilities.TwoWayCycledListWithSentinel;

public class ListStack<T> implements IStack<T> {

    private TwoWayCycledListWithSentinel<T> list;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ListStack() {
        list = new TwoWayCycledListWithSentinel<>();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TwoWayCycledListWithSentinel<T> getList() {
        return list;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean isFull() {
        return false;
    }

    @Override
    public T pop() throws EmptyStackException {
        if (this.isEmpty())
            throw new EmptyStackException();
        return list.delete(0);
    }

    @Override
    public void push(T elem) {
        list.add(0, elem);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public T top() throws EmptyStackException {
        if (this.isEmpty())
            throw new EmptyStackException();
        return list.get(0);
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append('[');
        if (!isEmpty()) {
            for (T item : list)
                buffer.append(item).append(", ");
            buffer.setLength(buffer.length() - 2);
        }
        buffer.append(']');
        return buffer.toString();
    }
}
