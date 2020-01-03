package com.Ukasz09.github.utilities;

public abstract class AbstractList<E> implements List<E> {
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append('[');
        if (!isEmpty()) {
            for (E item : this)
                buffer.append(item).append(", ");
            buffer.setLength(buffer.length() - 2);
        }
        buffer.append(']');
        return buffer.toString();
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (E item : this)
            hashCode ^= item.hashCode();
        return hashCode;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;
        if (getClass() != object.getClass())
            return false;
        return super.equals((List<E>) object);
    }

}
