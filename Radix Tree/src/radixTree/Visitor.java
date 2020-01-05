package radixTree;

public abstract class Visitor<T, R> {
    protected R result;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public Visitor() {
        this.result = null;
    }

    public Visitor(R initialValue) {
        this.result = initialValue;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    abstract public void visit(String key, RadixTreeNode<T> parent, RadixTreeNode<T> node);

    public R getResult() {
        return result;
    }
}