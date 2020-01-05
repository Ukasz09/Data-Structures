package avlTree;

import java.util.Collection;

public class AVLTree<T extends Comparable<T>> {
    private static final int SPACE_AMOUNT = 7;
    private Node<T> root;
    private int size;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class Node<K extends Comparable<K>> {
        private K data;
        private Node<K> left, right;
        private int height;
        private int bf;

        Node(K data) {
            this.data = data;
        }

        public K getData() {
            return data;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public AVLTree() {
        clear();
    }

    public AVLTree(Collection<? extends T> c) {
        addAll(c);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void insert(T data) {
        if (contains(data)) return;
        Node<T> newNode = new Node<>(data);
        root = insert(root, newNode);
        size++;
    }

    private Node<T> insert(Node<T> current, Node<T> addedNode) {
        if (current == null) {
            addedNode.bf = 0;
            addedNode.height = 0;
            return addedNode;
        }

        if (extendCompare(addedNode.data, current.data) > 0) {
            current.right = rotate(insert(current.right, addedNode));
        } else current.left = rotate(insert(current.left, addedNode));

        current = rotate(current);
        return current;
    }

    public void addAll(Collection<? extends T> c) {
        for (T thing : c)
            insert(thing);
    }

    public T remove(T data) {
        if (!contains(data))
            return null;
        root = rotate(remove(root, data));
        size--;
        return data;
    }

    private Node<T> remove(Node<T> current, T valToRemove) {
        if (extendCompare(current.data, valToRemove) == 0) {
            if (current.right == null && current.left == null)
                return null;
            else if (current.right == null)
                return rotate(current.left);
            else if (current.left == null)
                return rotate(current.right);
            else {
                Node<T> pre = current.left;
                Node<T> predecessor;
                if (pre.right == null) {
                    predecessor = pre;
                    predecessor.right = current.right;
                } else {
                    while (pre.right.right != null)
                        pre = pre.right;

                    predecessor = pre.right;
                    pre.right = predecessor.left;
                    predecessor.left = current.left;
                    predecessor.right = current.right;
                }
                return predecessor;
            }
        } else {
            if (extendCompare(valToRemove, current.data) > 0)
                current.right = rotate(remove(current.right, valToRemove));
            else current.left = rotate(remove(current.left, valToRemove));

            return rotate(current);
        }
    }

    private Node<T> rotate(Node<T> n) {
        if (n == null) return null;

        n = updateHeightAndBF(n);
        if (n.bf < -1) {
            if (n.right.bf > 0)
                n = rightLeftRotation(n);
            else n = leftRotation(n);
        } else if (n.bf > 1) {
            if (n.left.bf < 0)
                n = leftRightRotation(n);
            else n = right(n);
        }
        return n;
    }

    private Node<T> updateHeightAndBF(Node<T> n) {
        int left, right;
        left = n.left != null ? n.left.height : -1;
        right = n.right != null ? n.right.height : -1;
        n.bf = left - right;
        n.height = (Math.max(right, left)) + 1;
        return n;
    }

    private Node<T> leftRotation(Node<T> n) {
        Node<T> newRoot = n.right;
        Node<T> temp = n.right.left;
        n.right.left = n;
        n.right = temp;
        updateHeightAndBF(n);
        return newRoot;
    }

    private Node<T> right(Node<T> n) {
        Node<T> newRoot = n.left;
        Node<T> temp = n.left.right;
        n.left.right = n;
        n.left = temp;
        updateHeightAndBF(n);
        return newRoot;
    }

    private Node<T> leftRightRotation(Node<T> n) {
        n.left = leftRotation(n.left);
        n = right(n);
        return n;
    }

    private Node<T> rightLeftRotation(Node<T> n) {
        n.right = right(n.right);
        n = leftRotation(n);
        return n;
    }

    public boolean contains(T data) {
        if (isEmpty()) return false;
        return contains(root, data);
    }

    private boolean contains(Node<T> current, T searchingData) {
        if (current == null) return false;
        if (extendCompare(current.data, searchingData) == 0)
            return true;
        else {
            if (contains(current.right, searchingData))
                return true;
            else return contains(current.left, searchingData);
        }
    }

    private int extendCompare(T d1, T d2) {
        if (d1 == null && d2 == null)
            return 0;
        else if (d1 == null)
            return 1;
        else if (d2 == null)
            return -1;
        else return d1.compareTo(d2);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public Node search(T data) {
        Node<T> actual = root;
        while (actual != null && actual.data != data) {
            if (extendCompare(actual.data, data) > 0)
                actual = actual.left;
            else actual = actual.right;
        }

        return actual;
    }

    public void printTree() {
        printTreeFromNode(root, 0);
    }

    private void printTreeFromNode(Node root, int space) {
        if (root == null)
            return;
        space += SPACE_AMOUNT;
        printTreeFromNode(root.right, space);
        System.out.print("\n");
        for (int i = SPACE_AMOUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root.data + "\n");
        printTreeFromNode(root.left, space);
    }

    private Node min(Node node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    private Node max(Node node) {
        while (node.right != null)
            node = node.right;
        return node;
    }

    public void inOrderFromNode(Node node) {
        if (node != null) {
            inOrderFromNode(node.left);
            System.out.print(node.data + ", ");
            inOrderFromNode(node.right);
        }
    }

    public void preOrderFromNode(Node node) {
        if (node != null) {
            System.out.print(node.data + ", ");
            if (node.left != null)
                System.out.print(node.left.data + ", ");
            else System.out.print("-, ");

            if (node.right != null)
                System.out.println(node.right.data);
            else System.out.println("-");

            preOrderFromNode(node.left);
            preOrderFromNode(node.right);
        }
    }

    public void postOrderFromNode(Node node) {
        if (node != null) {
            postOrderFromNode(node.left);
            postOrderFromNode(node.right);
            System.out.print(node.data + ", ");

            if (node.left != null)
                System.out.print(node.left.data + ", ");
            else System.out.print("-, ");

            if (node.right != null)
                System.out.println(node.right.data);
            else System.out.println("-");
        }
    }

    public void inOrder() {
        inOrderFromNode(root);
    }

    public void preOrder() {
        preOrderFromNode(root);
    }

    public void postOrder() {
        postOrderFromNode(root);
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }

    public int size() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}