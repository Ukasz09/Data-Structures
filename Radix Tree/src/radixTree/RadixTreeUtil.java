package radixTree;

public abstract class RadixTreeUtil {

    public static <V> void printTree(RadixTree<V> tree) {
        printTree(tree.getRoot(), "");
    }

    private static <V> void printTree(RadixTreeNode<V> node, String outputPrefix) {
        if (node.hasValue())
            System.out.format("%s{%s : %s}%n", outputPrefix, node.getKey(), node.getValue());
        else System.out.format("%s{%s}%n", outputPrefix, node.getKey(), node.getValue());

        for (RadixTreeNode<V> child : node.getChild())
            printTree(child, outputPrefix + "\t");
    }
}
