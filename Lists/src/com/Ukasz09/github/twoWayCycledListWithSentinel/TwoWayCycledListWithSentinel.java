package com.Ukasz09.github.twoWayCycledListWithSentinel;

import com.Ukasz09.github.AbstractList;

import java.util.Iterator;

public class TwoWayCycledListWithSentinel<E> extends AbstractList<E> {

    private Element sentinel = null;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class Element {
        private E value;
        private Element next;
        private Element prev;

        E getValue() {
            return value;
        }

        void setValue(E value) {
            this.value = value;
        }

        Element getNext() {
            return next;
        }

        void setNext(Element next) {
            this.next = next;
        }

        Element getPrev() {
            return prev;
        }

        void setPrev(Element prev) {
            this.prev = prev;
        }

        Element(E data) {
            this.value = data;
        }

        void insertAfter(Element elem) {
            elem.setNext(this.getNext());
            elem.setPrev(this);
            this.getNext().setPrev(elem);
            this.setNext(elem);
        }

        void insertBefore(Element elem) {
            elem.setNext(this);
            elem.setPrev(this.getPrev());
            this.getPrev().setNext(elem);
            this.setPrev(elem);
        }

        void remove() {
            this.getNext().setPrev(this.getPrev());
            this.getPrev().setNext(this.getNext());
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class TWCIterator implements Iterator<E> {
        Element _current = sentinel;

        @Override
        public boolean hasNext() {
            return _current.getNext() != sentinel;
        }

        @Override
        public E next() {
            _current = _current.getNext();
            return _current.getValue();
        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public TwoWayCycledListWithSentinel() {
        sentinel = new Element(null);
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Element getElement(int index) {
        if (index < 0)
            throw new IndexOutOfBoundsException();
        Element elem = sentinel.getNext();
        int counter = 0;

        while (elem != sentinel && counter < index) {
            counter++;
            elem = elem.getNext();
        }

        if (elem == sentinel)
            throw new IndexOutOfBoundsException();
        return elem;
    }

    private Element getElement(E value) {
        Element elem = sentinel.getNext();

        while (elem != sentinel && !value.equals(elem.getValue()))
            elem = elem.getNext();

        if (elem == sentinel)
            return null;
        return elem;
    }

    @Override
    public boolean isEmpty() {
        return sentinel.getNext() == sentinel;
    }

    @Override
    public void clear() {
        sentinel.setNext(sentinel);
        sentinel.setPrev(sentinel);
    }

    @Override
    public E get(int index) {
        Element elem = getElement(index);
        return elem.getValue();
    }

    @Override
    public E set(int index, E value) {
        Element elem = getElement(index);
        E retValue = elem.getValue();
        elem.setValue(value);
        return retValue;
    }

    @Override
    public boolean add(E value) {
        Element newElem = new Element(value);
        sentinel.insertBefore(newElem);
        return true;
    }

    @Override
    public boolean add(int index, E value) {
        Element newElem = new Element(value);
        if (index == 0)
            sentinel.insertAfter(newElem);
        else {
            Element elem = getElement(index - 1);
            elem.insertAfter(newElem);
        }
        return true;
    }

    @Override
    public E delete(int index) {
        Element elem = getElement(index);
        elem.remove();

        return elem.getValue();
    }

    @Override
    public boolean delete(E value) {
        Element elem = getElement(value);
        if (elem == null) return false;
        elem.remove();

        return true;
    }

    public E deleteLastIndex() {
        Element retElem = sentinel.getPrev();
        if (!this.isEmpty()) {
            sentinel.setPrev(sentinel.getPrev().getPrev());
            sentinel.getPrev().setNext(sentinel);
            return retElem.getValue();
        } else throw new ArrayIndexOutOfBoundsException();

    }

    @Override
    public int size() {
        Element elem = sentinel.getNext();
        int counter = 0;
        while (elem != sentinel) {
            counter++;
            elem = elem.getNext();
        }
        return counter;
    }

    @Override
    public Iterator<E> iterator() {
        return new TWCIterator();
    }

}
