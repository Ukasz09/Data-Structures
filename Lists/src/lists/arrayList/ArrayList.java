package lists.arrayList;

import lists.AbstractList;
import lists.oneWayCycledSorted.Predicate;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class ArrayList<E> extends AbstractList<E> {
    private static final int DEFAULT_ARR_CAPACITY = 20;
    private final int initialCapacity;

    private E[] array;
    private int amountOfElements;
    private int sortCounter;
    private Predicate predicate;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class InnerIterator implements Iterator<E> {
        int _pos = 0;

        @Override
        public boolean hasNext() {
            return _pos < amountOfElements;
        }

        @Override
        public E next() {
            return array[_pos++];
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class InnerListIterator implements ListIterator<E> {
        int _pos = 0;

        @Override
        public boolean hasNext() {
            return _pos < amountOfElements;
        }

        @Override
        public boolean hasPrevious() {

            return _pos >= 0;
        }

        @Override
        public E next() {
            return array[_pos++];
        }

        @Override
        public int nextIndex() {
            return _pos;
        }

        @Override
        public E previous() {
            return array[--_pos];
        }

        @Override
        public int previousIndex() {
            return _pos - 1;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void add(E Value) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(E e) {
            throw new UnsupportedOperationException();
        }

    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity, Predicate predicate) {
        if (capacity <= 0)
            capacity = DEFAULT_ARR_CAPACITY;
        this.predicate = predicate;
        sortCounter = 0;
        initialCapacity = capacity;
        array = (E[]) (new Object[capacity]);
        amountOfElements = 0;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList() {
        this(DEFAULT_ARR_CAPACITY, null);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public E get(int index) {
        checkOutOfBounds(index);
        return array[index];
    }

    @Override
    public E set(int index, E element) {
        checkOutOfBounds(index);
        E retValue = array[index];
        array[index] = element;

        return retValue;
    }

    public int getSortCounter() {
        return sortCounter;
    }

    public void setSortCounter(int sortCounter) {
        if (sortCounter >= 0)
            this.sortCounter = sortCounter;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity(int capacity) {
        if (capacity > array.length) {
            E[] copy = (E[]) (new Object[capacity + capacity / 2]);
            System.arraycopy(array, 0, copy, 0, amountOfElements);
            array = copy;
            sortCounter += amountOfElements;
        }
    }

    private void checkOutOfBounds(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= amountOfElements) throw new IndexOutOfBoundsException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        array = (E[]) (new Object[initialCapacity]);
        amountOfElements = 0;
    }

    @Override
    public boolean isEmpty() {
        return amountOfElements == 0;
    }

    @Override
    public int size() {
        return amountOfElements;
    }

    @Override
    public boolean add(E value) {
        ensureCapacity(amountOfElements + 1);
        array[amountOfElements] = value;
        amountOfElements++;
        sortCounter++;

        return true;
    }

    @Override
    public boolean add(int index, E value) {
        if (index == amountOfElements) {
            ensureCapacity(amountOfElements + 1);
            array[index] = value;
            amountOfElements++;
            sortCounter++;

            return true;
        }

        checkOutOfBounds(index);
        ensureCapacity(amountOfElements + 1);
        System.arraycopy(array, index, array, index + 1, amountOfElements - index);
        array[index] = value;

        sortCounter += amountOfElements - index;
        amountOfElements++;

        return true;
    }

    public boolean addWithSort(E newValue) {
        if (this.isEmpty()) {
            array[0] = newValue;
            amountOfElements = 1;
            sortCounter++;

            return true;
        }

        int actualId = 0;
        E actualValue;
        while (actualId < size()) {
            actualValue = array[actualId];

            if (predicate.accept(actualValue, newValue)) {
                add(actualId, newValue);
                return true;
            }
            sortCounter++;
            actualId++;
        }
        add(newValue);
        return true;
    }

    @Override
    public E delete(int index) {
        checkOutOfBounds(index);
        E retValue = array[index];
        int copyFrom = index + 1;

        if (copyFrom < amountOfElements)
            System.arraycopy(array, copyFrom, array, index, amountOfElements - copyFrom);
        amountOfElements--;
        return retValue;
    }

    @Override
    public boolean delete(E value) {
        int pos = 0;
        while (pos < amountOfElements && !array[pos].equals(value))
            pos++;

        if (pos < amountOfElements) {
            delete(pos);
            return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new InnerIterator();
    }

    public ListIterator<E> listIterator() {
        return new InnerListIterator();
    }

    public void sortArr(Predicate predicate) {
        int size = this.size();
        if (size == 1)
            return;
        if (size < 1)
            throw new NoSuchElementException();
        int n = amountOfElements;
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++) {
                if (predicate.accept(array[j], array[j + 1])) {
                    E temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
                sortCounter++;
            }
    }
}
