package lists.oneWayCycledSorted;

public interface Predicate<E> {
    boolean accept(E actualElement, E newElement);
}
