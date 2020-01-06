package com.github.Ukasz09.graphs;

import java.util.*;

public class AdjListDigraphWeighted<V> {
    private Map<V, List<Edge<V>>> vertices = new HashMap<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class Edge<V> {
        private V vertex;
        private int weight;

        Edge(V vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        public V getVertex() {
            return vertex;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return "[v=" + vertex + ", w=" + weight + "]";
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean add(V vertex) {
        if (vertices.containsKey(vertex))
            return false;
        vertices.put(vertex, new ArrayList<>());
        return true;
    }

    public boolean contains(V vertex) {
        return vertices.containsKey(vertex);
    }

    public void add(V from, V to, int weight) {
        this.add(from);
        this.add(to);
        vertices.get(from).add(new Edge<V>(to, weight));
    }

    public int outDegree(int vertex) {
        return vertices.get(vertex).size();
    }

    public int inDegree(V vertex) {
        return inboundsEdges(vertex).size();
    }

    public List<V> inboundsEdges(V vertex) {
        List<V> inList = new ArrayList<>();
        for (V to : vertices.keySet())
            for (Edge edge : vertices.get(to))
                if (edge.vertex.equals(vertex))
                    inList.add(to);

        return inList;
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        for (V vertex : vertices.keySet())
            s.append("\n    " + vertex + " -> " + vertices.get(vertex));
        return s.toString();
    }

    public int getNumberOfEdges() {
        int sum = 0;
        for (List<Edge<V>> vertexEdgeList : vertices.values())
            sum += vertexEdgeList.size();
        return sum;
    }

    public int getWeight(V from, V to) {
        for (Edge<V> edge : vertices.get(from))
            if (edge.vertex.equals(to))
                return edge.weight;
        return -1;
    }

    public boolean isEdge(V from, V to) {
        for (Edge<V> edge : vertices.get(from))
            if (edge.vertex.equals(to))
                return true;
        return false;
    }

    public Map<V, List<Edge<V>>> getVertices() {
        return vertices;
    }
}