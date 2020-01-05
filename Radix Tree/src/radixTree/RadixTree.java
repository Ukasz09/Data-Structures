package radixTree;

import java.util.ArrayList;
import java.util.Iterator;

public class RadixTree<T> {
    private RadixTreeNode<T> root;
    private long size;

    public RadixTree() {
        root = new RadixTreeNode<>();
        root.setKey("");
        size = 0;
    }

    public T find(String key) {
        Visitor<T, T> visitor = new Visitor<T, T>() {
            public void visit(String key, RadixTreeNode<T> parent, RadixTreeNode<T> node) {
                if (node.hasValue())
                    result = node.getValue();
            }
        };
        visit(key, visitor);
        return visitor.getResult();
    }

    private <R> void visit(String key, Visitor<T, R> visitor) {
        if (root != null) {
            visit(key, visitor, null, root);
        }
    }

    private <R> void visit(String prefix, Visitor<T, R> visitor, RadixTreeNode<T> parent, RadixTreeNode<T> node) {
        int numberOfMatchingChar = node.getNumberOfMatchingChar(prefix);

        if (numberOfMatchingChar == prefix.length() && numberOfMatchingChar == node.getKey().length())
            visitor.visit(prefix, parent, node);

        else if (node.getKey().equals("") || (numberOfMatchingChar < prefix.length() && numberOfMatchingChar >= node.getKey().length())) {
            String newText = prefix.substring(numberOfMatchingChar);
            for (RadixTreeNode<T> child : node.getChild()) {
                if (child.getKey().startsWith(newText.charAt(0) + "")) {
                    visit(newText, visitor, node, child);
                    break;
                }
            }
        }
    }

    public boolean replace(String key, final T value) {
        Visitor<T, T> visitor = new Visitor<T, T>() {

            public void visit(String key, RadixTreeNode<T> parent, RadixTreeNode<T> node) {
                if (node.hasValue()) {
                    node.setValue(value);
                    result = value;
                } else result = null;
            }
        };
        visit(key, visitor);
        return visitor.getResult() != null;
    }

    public boolean delete(String key) {
        Visitor<T, Boolean> visitor = new Visitor<T, Boolean>(Boolean.FALSE) {
            public void visit(String key, RadixTreeNode<T> parent, RadixTreeNode<T> node) {
                result = node.hasValue();
                if (result) {
                    if (node.getChild().size() == 0) {
                        Iterator<RadixTreeNode<T>> it = parent.getChild().iterator();
                        while (it.hasNext()) {
                            if (it.next().getKey().equals(node.getKey())) {
                                it.remove();
                                break;
                            }
                        }
                        if (parent.getChild().size() == 1 && !parent.hasValue())
                            mergeNodes(parent, parent.getChild().get(0));
                    } else if (node.getChild().size() == 1)
                        mergeNodes(node, node.getChild().get(0));
                    else node.setHasValue(false);
                }
            }


            private void mergeNodes(RadixTreeNode<T> parent, RadixTreeNode<T> child) {
                parent.setKey(parent.getKey() + child.getKey());
                parent.setHasValue(child.hasValue());
                parent.setValue(child.getValue());
                parent.setChild(child.getChild());
            }
        };

        visit(key, visitor);
        if (visitor.getResult())
            size--;

        return visitor.getResult();
    }

    public boolean insert(String key, T value) {
        boolean insertSuccessful = insert(key, root, value);
        if (insertSuccessful)
            size++;
        return insertSuccessful;
    }

    private boolean insert(String key, RadixTreeNode<T> node, T value) {
        int numberOfMatchingChar = node.getNumberOfMatchingChar(key);
        String substring = key.substring(numberOfMatchingChar, key.length());
        if (node.getKey().equals("") || numberOfMatchingChar == 0 || (numberOfMatchingChar < key.length() && numberOfMatchingChar >= node.getKey().length())) {
            boolean flag = false;
            String newText = substring;
            for (RadixTreeNode<T> child : node.getChild()) {
                if (child.getKey().startsWith(newText.charAt(0) + "")) {
                    flag = true;
                    insert(newText, child, value);
                    break;
                }
            }

            if (!flag) {
                RadixTreeNode<T> n = new RadixTreeNode<T>();
                n.setKey(newText);
                n.setHasValue(true);
                n.setValue(value);
                node.getChild().add(n);
            }
        } else if (numberOfMatchingChar == key.length() && numberOfMatchingChar == node.getKey().length()) {
            if (node.hasValue())
                return false;
            node.setHasValue(true);
            node.setValue(value);
        } else if (numberOfMatchingChar > 0 && numberOfMatchingChar < node.getKey().length()) {
            RadixTreeNode<T> newNode = new RadixTreeNode<T>();
            newNode.setKey(node.getKey().substring(numberOfMatchingChar));
            newNode.setHasValue(node.hasValue());
            newNode.setValue(node.getValue());
            newNode.setChild(node.getChild());

            node.setKey(key.substring(0, numberOfMatchingChar));
            node.setHasValue(false);
            node.setChild(new ArrayList<>());
            node.getChild().add(newNode);

            if (numberOfMatchingChar < key.length()) {
                RadixTreeNode<T> n2 = new RadixTreeNode<T>();
                n2.setKey(substring);
                n2.setHasValue(true);
                n2.setValue(value);
                node.getChild().add(n2);
            }

            else {
                node.setValue(value);
                node.setHasValue(true);
            }
        }

        else {
            RadixTreeNode<T> n = new RadixTreeNode<T>();
            n.setKey(node.getKey().substring(numberOfMatchingChar));
            n.setChild(node.getChild());
            n.setHasValue(node.hasValue());
            n.setValue(node.getValue());
            node.setKey(key);
            node.setHasValue(true);
            node.setValue(value);
            node.getChild().add(n);
        }

        return true;
    }

    public boolean contains(String key) {
        Visitor<T, Boolean> visitor = new Visitor<T, Boolean>(Boolean.FALSE) {
            public void visit(String key, RadixTreeNode<T> parent, RadixTreeNode<T> node) {
                result = node.hasValue();
            }
        };
        visit(key, visitor);
        return visitor.getResult();
    }

    public long getSize() {
        return size;
    }

    public RadixTreeNode<T> getRoot() {
        return root;
    }
}