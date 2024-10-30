package com.example.structure2025;

import java.util.Objects;

public class Node implements Comparable<Node> {
    // Private fields
    private Object data;
    private Node nextNode;

    // Constructor
    public Node(Object data) {
        this.data = data;
    }

    // Getter for data
    public Object getData() {
        return data;
    }

    // Setter for data
    public void setData(Object data) {
        this.data = data;
    }

    // Getter for nextNode
    public Node getNextNode() {
        return nextNode;
    }

    // Setter for nextNode
    public void setNextNode(Node nextNode) {
        this.nextNode = nextNode;
    }

    @Override
    public boolean equals(Object o) {

            return ((Student) this.data).getId().equalsIgnoreCase((String) o);

    }

    @Override
    public String toString() {
        return
                 data.toString();
    }

    @Override
    public int compareTo(Node o) {
        return (int)(((Student)this.data).getAdmark()-((Student)o.getData()).getAdmark());
    }

}