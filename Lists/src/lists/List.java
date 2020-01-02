package lists;

public interface List<E> extends Iterable<E>{

    boolean add(E value);

    boolean add(int index, E value);

    E delete(int index);

    boolean delete(E value);

    E get(int index);

    E set(int index, E value);

    boolean isEmpty();

    int size();

    void clear();
}
