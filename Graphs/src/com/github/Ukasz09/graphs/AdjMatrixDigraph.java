package com.github.Ukasz09.graphs;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class AdjMatrixDigraph {
    private int vertex;
    private int edges;
    private boolean[][] adj;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private class AdjIterator implements Iterator<Integer>, Iterable<Integer> {
        private int v;
        private int w = 0;

        AdjIterator(int v) {
            this.v = v;
        }

        public Iterator<Integer> iterator() {
            return this;
        }

        public boolean hasNext() {
            while (w < vertex) {
                if (adj[v][w]) return true;
                w++;
            }
            return false;
        }

        public Integer next() {
            if (hasNext()) return w++;
            else throw new NoSuchElementException();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public AdjMatrixDigraph() {
        vertex = 0;
        edges = 0;
        adj = new boolean[1][1];
        adj[0][0] = false;
    }

    public AdjMatrixDigraph(int numberOfVertex) {
        if (numberOfVertex < 0) throw new RuntimeException("Number of vertices can not be negative");
        this.vertex = numberOfVertex;
        this.edges = 0;
        this.adj = new boolean[numberOfVertex][numberOfVertex];
    }

    public AdjMatrixDigraph(int numberOfVertex, int numberOfEdges) {
        this(numberOfVertex);
        if (numberOfEdges < 0) throw new RuntimeException("Number of vertices can not be negative");
        if (numberOfEdges > numberOfVertex * numberOfVertex) throw new RuntimeException("Too many edges");

        while (this.edges != numberOfEdges) {
            int v1 = (int) (Math.random() * numberOfVertex) + 1;
            int v2 = (int) (Math.random() * numberOfVertex) + 1;
            add(v1, v2);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void add(int v1, int v2) {
        if (!adj[v1][v1]) edges++;
        adj[v1][v2] = true;
    }

    public void clearMatrix(boolean[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                adj[i][j] = false;
    }

    public Iterable<Integer> adj(int v) {
        return new AdjIterator(v);
    }

    public int getVertex() {
        return vertex;
    }

    public int getEdges() {
        return edges;
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        boolean anyEdges;
        for (int v = 0; v < vertex; v++) {
            anyEdges = true;
            s.append(v + " -> [");
            for (int w : adj(v)) {
                s.append(w + ", ");
                anyEdges = false;
            }
            if (!anyEdges)
                s.append("\b\b");
            s.append("]\n");
        }
        return s.toString();
    }
}