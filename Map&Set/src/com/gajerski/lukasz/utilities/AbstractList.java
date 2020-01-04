package com.gajerski.lukasz.utilities;

public abstract class AbstractList<T> implements IList<T> {

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append('[');
        if (!isEmpty()) {
            for (T item : this)
                buffer.append(item).append(", ");
            buffer.setLength(buffer.length() - 2);
        }
        buffer.append(']');
        return buffer.toString();
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (T item : this)
            hashCode ^= item.hashCode();
        return hashCode;
    }

    public void swap(int left, int right) {
        if (left != right) {
            T temp = this.get(left);
            this.set(left, this.get(right));
            this.set(right, temp);
        }
    }
}
