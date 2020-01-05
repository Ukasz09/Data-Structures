package com.github.Ukasz09.BST;

public class BinarySearchTree {
    private static final int SPACE_AMOUNT = 7;
    private Node root = null;
    private int counter;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class TreeException extends Throwable {

        TreeException(String msg) {
            super(msg);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class Node {
        int key;
        Node left, right, parent = null;

        Node(int key) {
            this.key = key;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void insert(int key) {
        if (root == null)
            root = new Node(key);
        else {
            Node actual = root;
            Node parent = null;

            while (actual != null) {
                parent = actual;
                actual = (actual.key > key) ? actual.left : actual.right;
            }

            if (parent.key > key) {
                parent.left = new Node(key);
                parent.left.parent = parent;
            } else {
                parent.right = new Node(key);
                parent.right.parent = parent;
            }
        }
    }

    public Node search(int key) throws TreeException {
        counter = 0;
        Node actual = root;
        while (actual != null && actual.key != key) {
            actual = (actual.key > key) ? actual.left : actual.right;
            counter++;
        }

        if (actual == null)
            throw new TreeException("Key not found");
        return actual;
    }

    public Node search(Node root) throws TreeException {
        return search(root.key);
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

    private Node getSuccessor(int key) throws TreeException {
        Node node = this.search(key);
        if (node.right != null) {
            node = node.right;
            while (node.left != null)
                node = node.left;
            return node;
        } else if (node.right == null && node != root && node != this.max(root)) {
            Node parent = node.parent;
            while (parent != root && parent.key < node.key)
                parent = parent.parent;
            return parent;
        } else
            throw new TreeException("Successor not found");
    }

    private Node predecessor(int key) throws TreeException {
        Node node = this.search(key);

        if (node.left != null) {
            node = node.left;
            while (node.right != null)
                node = node.right;
            return node;
        } else if (node.left == null && node != root && node != this.min(root)) {
            Node parent = node.parent;
            while (parent != root && parent.key > node.key)
                parent = parent.parent;
            return parent;
        } else
            throw new TreeException("Predecessor not found");
    }

    public Node remove(int key) throws TreeException {
        Node node = this.search(key);
        Node parent = node.parent;
        Node tmp;
        if (node.left != null && node.right != null) {
            tmp = this.remove(this.getSuccessor(key).key);
            tmp.left = node.left;
            if (tmp.left != null)
                tmp.left.parent = tmp;
            tmp.right = node.right;
            if (tmp.right != null)
                tmp.right.parent = tmp;
        } else
            tmp = (node.left != null) ? node.left : node.right;

        if (tmp != null)
            node.parent = parent;

        if (parent == null)
            root = tmp;
        else if (parent.left == node)
            parent.left = tmp;
        else parent.right = tmp;

        return node;
    }

    public void clearTree() {
        if (root != null) {
            root.right = null;
            root.left = null;
            root = null;
        }
    }

    public void inOrderFromNode(Node node) {
        if (node != null) {
            inOrderFromNode(node.left);
            System.out.print(node.key + ", ");
            inOrderFromNode(node.right);
        }
    }

    public void preOrderFromNode(Node node) {
        if (node != null) {
            System.out.print(node.key + ", ");
            if (node.left != null)
                System.out.print(node.left.key + ", ");
            else System.out.print("-, ");

            if (node.right != null)
                System.out.println(node.right.key);
            else System.out.println("-");

            preOrderFromNode(node.left);
            preOrderFromNode(node.right);
        }
    }

    public void postOrderFromNode(Node node) {
        if (node != null) {
            postOrderFromNode(node.left);
            postOrderFromNode(node.right);
            System.out.print(node.key + ", ");

            if (node.left != null)
                System.out.print(node.left.key + ", ");
            else System.out.print("-, ");

            if (node.right != null)
                System.out.println(node.right.key);
            else System.out.println("-");
        }
    }

    public void levelOrder() {
        for (int i = 1; i <= treeHeight(root); i++)
            printGivenLevel(root, i);
    }

    private void printGivenLevel(Node root, int level) {
        if (root == null)
            return;
        if (level == 1)
            System.out.print(root.key + " ");
        else if (level > 1) {
            printGivenLevel(root.left, level - 1);
            printGivenLevel(root.right, level - 1);
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

    public int treeHeight(Node root) {
        if (root == null)
            return 0;

        int lheight = treeHeight(root.left);
        int rheight = treeHeight(root.right);
        if (lheight > rheight)
            return (lheight + 1);
        else return (rheight + 1);
    }

    private void printTreeFromNode(Node root, int space) {
        if (root == null)
            return;

        space += SPACE_AMOUNT;
        printTreeFromNode(root.right, space);
        System.out.print("\n");
        for (int i = SPACE_AMOUNT; i < space; i++)
            System.out.print(" ");
        System.out.print(root.key + "\n");
        printTreeFromNode(root.left, space);
    }

    public void printTree() {
        printTreeFromNode(root, 0);
    }

    private Node leftRotate(Node n) {
        if (n.right != null) {
            Node rightChild = n.right;
            n.right = rightChild.right;
            rightChild.right = rightChild.left;
            rightChild.left = n.left;
            n.left = rightChild;

            int temp = n.key;
            n.key = rightChild.key;
            rightChild.key = temp;
        }
        return n;
    }

    private Node rightRotate(Node n) {
        if (n.left != null) {
            Node leftChildren = n.left;
            n.left = leftChildren.left;
            leftChildren.left = leftChildren.right;
            leftChildren.right = n.right;
            n.right = leftChildren;

            int temp = n.key;
            n.key = leftChildren.key;
            leftChildren.key = temp;
        }

        return n;
    }

    private Node createRightVine(Node root) {
        while (root.left != null)
            root = rightRotate(root);

        if (root.right != null)
            root.right = createRightVine(root.right);

        return root;
    }

    private void createBackbone() {
        Node grandParent = null;
        Node parent = root;
        Node leftChild;

        while (null != parent) {
            leftChild = parent.left;
            if (null != leftChild) {
                grandParent = rotateRight(grandParent, parent, leftChild);
                rightRotate(parent);
                parent = leftChild;
            } else {
                grandParent = parent;
                parent = parent.right;
            }
        }
    }

    private Node rotateRight(Node grandParent, Node parent, Node leftChild) {
        if (null != grandParent)
            grandParent.right = leftChild;
        else root = leftChild;

        parent.left = leftChild.right;
        leftChild.right = parent;
        return grandParent;
    }

    private int getNodeCount(Node root) {
        if (root == null)
            return 0;
        int i = 1;
        while (root.right != null) {
            root = root.right;
            i++;
        }
        return i;
    }

    private Node balanceVine(Node root, int nodeCount) {
        int times = (int) (Math.log(nodeCount) / Math.log(2));
        Node newRoot = root;
        for (int i = 0; i < times; i++) {
            newRoot = leftRotate(newRoot);
            root = newRoot.right;
            for (int j = 0; j < nodeCount / 2 - 1; j++) {
                root = leftRotate(root);
                root = root.right;
            }
            nodeCount >>= 1;
        }

        return newRoot;
    }

    public void optimizationDSW() {
        if (root != null) {
            createRightVine(root);
            balanceVine(root, getNodeCount(root));
        }
    }

    public void dsw2() {
        if (root != null) {
            createBackbone();
            balanceVine(root, getNodeCount(root));
        }
    }

}