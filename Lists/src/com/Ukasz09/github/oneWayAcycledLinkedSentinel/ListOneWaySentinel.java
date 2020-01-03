package com.Ukasz09.github.oneWayAcycledLinkedSentinel;

import com.Ukasz09.github.AbstractList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListOneWaySentinel<E> extends AbstractList<E> {
    private Element sentinel;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class Element {
        private E value;
        private Element next;

        Element(E data) {
            this.value = data;
            this.setNext(null);
        }

        E getValue() {
            return value;
        }

        Element getNext() {
            return next;
        }

        void setValue(E value) {
            this.value = value;
        }

        void setNext(Element next) {
            this.next = next;
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class InnerIterator implements Iterator<E> {
        Element actElem;

        InnerIterator() {
            actElem = sentinel.getNext();
        }

        @Override
        public boolean hasNext() {
            return actElem != null;
        }

        @Override
        public E next() {
            E value = actElem.getValue();
            actElem = actElem.getNext();
            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ListOneWaySentinel() {
        sentinel = new Element(null);
        sentinel.setNext(sentinel);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Element getElement(int index) throws IndexOutOfBoundsException {
        if (index < 0) throw new IndexOutOfBoundsException();
        if (index == 0) {
            Element retElem = sentinel.getNext();
            if (retElem == sentinel)
                throw new IndexOutOfBoundsException();
            return sentinel.getNext();
        }

        Element actElem = sentinel.getNext();
        while (index > 0 && actElem != null) {
            actElem = actElem.getNext();
            index--;
        }

        if (actElem == null)
            throw new IndexOutOfBoundsException();
        return actElem;
    }

    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    @Override
    public boolean add(E value) {
        Element newElem = new Element(value);
        if (isEmpty()) {
            sentinel.setNext(newElem);
            return true;
        }

        Element tail = sentinel.getNext();
        while (tail.getNext() != null) {
            tail = tail.getNext();
        }
        tail.setNext(newElem);
        return true;
    }

    @Override
    public boolean add(int index, E value) throws IndexOutOfBoundsException {
        if (index < 0) throw new IndexOutOfBoundsException();

        Element newElem = new Element(value);
        if (index == 0) {
            if (!isEmpty()) {
                newElem.setNext(sentinel.getNext());
                sentinel.setNext(newElem);
            } else {
                sentinel.setNext(newElem);
                newElem.setNext(null);
            }
            return true;
        }

        Element actElem = getElement(index - 1);
        newElem.setNext(actElem.getNext());
        actElem.setNext(newElem);
        return true;
    }

    @Override
    public E delete(int index) throws IndexOutOfBoundsException {
        if (index < 0) throw new IndexOutOfBoundsException();
        if (this.isEmpty()) throw new NoSuchElementException();
        if (index == 0) {
            E valueToReturn = sentinel.getNext().getValue();
            sentinel.setNext(sentinel.getNext().getNext());
            return valueToReturn;
        }

        Element prevElem = getElement(index - 1);
        if (prevElem.getNext() == null)
            throw new IndexOutOfBoundsException();

        E retValue = prevElem.getNext().getValue();
        prevElem.setNext(prevElem.getNext().getNext());
        return retValue;
    }

    @Override
    public boolean delete(E value) {
        if (this.isEmpty())
            throw new NoSuchElementException();
        //removing head
        if (sentinel.getNext().getValue().equals(value)) {
            sentinel.setNext(sentinel.getNext().getNext());
            return true;
        }

        Element prevElem = sentinel.getNext();
        while (prevElem.getNext() != null && !prevElem.getNext().getValue().equals(value))
            prevElem = prevElem.getNext();

        if (prevElem.getNext() == null)
            return false;
        prevElem.setNext(prevElem.getNext().getNext());
        return true;
    }

    @Override
    public int size() {
        Element actElem = sentinel.getNext();
        if (isEmpty())
            return 0;

        int counter = 0;
        while (actElem != null) {
            counter++;
            actElem = actElem.getNext();
        }
        return counter;
    }

    @Override
    public void clear() {
        sentinel.setNext(sentinel);
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        Element elem = getElement(index);
        return elem.getValue();
    }

    @Override
    public E set(int index, E data) {
        Element actElem = getElement(index);
        E elemData = actElem.getValue();
        actElem.setValue(data);

        return elemData;
    }
}
