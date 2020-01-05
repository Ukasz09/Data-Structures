package radixTree;

import java.util.ArrayList;
import java.util.List;

class RadixTreeNode<T> {
    private String key;
    private List<RadixTreeNode<T>> child;
    private boolean hasValue;
    private T value;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public RadixTreeNode() {
        key = "";
        child = new ArrayList<>();
        hasValue = false;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public int getNumberOfMatchingChar(String key) {
        int numberMatchingChar = 0;
        while (numberMatchingChar < key.length() && numberMatchingChar < this.getKey().length()) {

            if (key.charAt(numberMatchingChar) != this.getKey().charAt(numberMatchingChar))
                break;

            numberMatchingChar++;
        }
        return numberMatchingChar;
    }

    @Override
    public String toString() {
        return key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T data) {
        this.value = data;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String value) {
        this.key = value;
    }

    public boolean hasValue() {
        return hasValue;
    }

    public void setHasValue(boolean datanode) {
        this.hasValue = datanode;
    }

    public List<RadixTreeNode<T>> getChild() {
        return child;
    }

    public void setChild(List<RadixTreeNode<T>> child) {
        this.child = child;
    }
}