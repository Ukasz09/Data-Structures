package lists.oneWayCycledSorted;

import lists.AbstractList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ListOneWayCycled<E> extends AbstractList<E> {
    private Element head;
    private Element tail;
    private Predicate predicate;
    private int sortCounter;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class Element {
        private E value;
        private Element next;

        Element(E data) {
            this.value = data;
            this.setNext(head);
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

        void insertAfter(Element newElem) {
            newElem.setNext(this.getNext());
            this.setNext(newElem);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class InnerIterator implements Iterator<E> {
        Element actualElem;
        boolean noneIterationDid = true;

        InnerIterator() {
            actualElem = head;
        }

        @Override
        public boolean hasNext() {
            return actualElem != tail.getNext() || noneIterationDid;
        }

        @Override
        public E next() {
            noneIterationDid = false;
            E value = actualElem.getValue();
            actualElem = actualElem.getNext();

            return value;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ListOneWayCycled(Predicate predicate) {
        head = new Element(null);
        tail = head;
        head.setNext(tail);
        tail.setNext(head);
        this.predicate = predicate;
        sortCounter = 0;
    }

    private Element getElement(int index) throws IndexOutOfBoundsException {
        if (index < 0) throw new IndexOutOfBoundsException();
        if (index == 0)
            return head;
        Element actElem = head;
        while ((index > 0) && (actElem.getNext() != head)) {
            actElem = actElem.getNext();
            index--;
        }

        if (index == 0)
            return actElem;
        else throw new IndexOutOfBoundsException();
    }

    private Element getElement(E value) {
        Element elem = head;
        while (elem.getNext() != head && !value.equals(elem.getValue()))
            elem = elem.getNext();

        if (elem.getValue().equals(value))
            return elem;
        else throw new NoSuchElementException();
    }

    private Element getPreviousElement(E value) {
        Element elem = head;
        while (elem.getNext() != head && !value.equals(elem.getNext().getValue()))
            elem = elem.getNext();

        if (elem.getValue().equals(value))
            return elem;
        else throw new IndexOutOfBoundsException();
    }

    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        Element elem = getElement(index);
        return elem.getValue();
    }

    public int getSortCounter() {
        return sortCounter;
    }

    @Override
    public E set(int index, E data) {
        Element actElem = getElement(index);
        E valueToReturn = actElem.getValue();
        actElem.setValue(data);

        return valueToReturn;
    }

    public void setSortCounter(int sortCounter) {
        if (sortCounter >= 0)
            this.sortCounter = sortCounter;
    }

    public boolean isEmpty() {
        return ((head.getValue() == null) && (head.getNext() == head));
    }

    @Override
    public boolean add(E value) {
        if (this.isEmpty())
            head.value = value;
        else {
            Element newElem = new Element(value);
            tail.insertAfter(newElem);
            tail = newElem;
        }

        return true;
    }

    @Override
    public boolean add(int index, E value) {
        if (index < 0) throw new IndexOutOfBoundsException();
        Element newElem = new Element(value);
        if (index == 0) {
            E tmpValue = head.getValue();
            head.setValue(newElem.getValue());
            newElem.setValue(tmpValue);

            head.insertAfter(newElem);
            return true;
        }

        Element elem = getElement(index - 1);
        elem.insertAfter(newElem);

        if (newElem.getNext() == head)
            tail = newElem;

        return true;
    }

    public boolean addWithSort(E value) {
        if (this.isEmpty()) {
            head.value = value;
            this.tail = head;
            sortCounter++;
            return true;
        }
        Element newElem = new Element(value);
        Element actElem = head;

        if (predicate.accept(head.getValue(), newElem.getValue())) {
            E tmpValue = head.getValue();
            head.setValue(newElem.getValue());
            newElem.setValue(tmpValue);
            actElem.insertAfter(newElem);

            if (newElem.getNext() == head)
                this.tail = newElem;
            sortCounter++;
            return true;
        }

        while (actElem.getNext() != head) {
            if (predicate.accept(actElem.getNext().getValue(), newElem.getValue())) {

                actElem.insertAfter(newElem);

                if (newElem.getNext() == head)
                    this.tail = newElem;

                sortCounter++;
                return true;
            }
            sortCounter++;
            actElem = actElem.getNext();
        }

        actElem.insertAfter(newElem);
        if (newElem.getNext() == head)
            this.tail = newElem;
        sortCounter++;
        return true;
    }

    @Override
    public E delete(int index) throws IndexOutOfBoundsException {
        if (index < 0) throw new IndexOutOfBoundsException();
        if (this.isEmpty()) throw new NoSuchElementException();
        if (index == 0) {
            Element elemToReturn = head;
            head = head.getNext();
            tail.setNext(head);
            return elemToReturn.getValue();
        }

        Element elem = getElement(index - 1);
        if (elem == tail)
            throw new IndexOutOfBoundsException();
        elem.setNext(elem.getNext().getNext());
        return elem.getValue();
    }

    @Override
    public boolean delete(E value) {
        if (this.isEmpty())
            throw new NoSuchElementException();

        Element elem = getElement(value);
        if (elem == null) return false;
        if (elem == head) {
            head = head.getNext();
            return true;
        }

        Element prevElem = getPreviousElement(value);
        if (elem == tail)
            tail = prevElem;
        prevElem.setNext(prevElem.getNext().getNext());
        return true;
    }

    @Override
    public int size() {
        if (this.isEmpty())
            return 0;
        int counter = 0;
        Element actElem = head;
        while (actElem.getNext() != head) {
            counter++;
            actElem = actElem.getNext();
        }
        counter += 1;
        return counter;
    }

    @Override
    public void clear() {
        head.setNext(head);
        head.value = null;
        tail = head;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    public void sortList(Predicate predicate) {
        int size = this.size();
        if (size == 1)
            return;
        if (size < 1)
            throw new NoSuchElementException();
        Element prevElem;
        Element actElem;
        E tmpValue;
        int i = 0;
        boolean doAnyChange = true;
        sortCounter = 0;

        while (doAnyChange) {
            doAnyChange = false;
            actElem = head;
            i = 0;
            while (i < size - 1) {
                prevElem = actElem;
                actElem = actElem.getNext();
                if (predicate.accept(prevElem.getValue(), actElem.getValue())) {
                    doAnyChange = true;
                    tmpValue = prevElem.getValue();
                    prevElem.setValue(actElem.getValue());
                    actElem.setValue(tmpValue);
                }
                i++;
                sortCounter++;
            }
        }

    }

}
