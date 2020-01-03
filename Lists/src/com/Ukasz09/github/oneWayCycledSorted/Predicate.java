package com.Ukasz09.github.oneWayCycledSorted;

public interface Predicate<E> {
    boolean accept(E actualElement, E newElement);
}
