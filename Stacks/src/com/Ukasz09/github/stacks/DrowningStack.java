package com.Ukasz09.github.stacks;

public class DrowningStack<T> extends ListStack<T> {
    private final static int DEFAULT_STACK_SIZE = 10;
    private int stackSize;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public DrowningStack(int stackSize) {
        super();
        this.stackSize = (stackSize > 0) ? stackSize : DEFAULT_STACK_SIZE;
    }

    public DrowningStack() {
        stackSize = DEFAULT_STACK_SIZE;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void push(T elem) {
        if (size() >= stackSize)
            getList().deleteLastIndex();
        getList().add(0, elem);
    }
}
