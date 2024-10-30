package com.example.structure2025;

public class DoubleNode {
    // Private fields
    private Object data;
    private DoubleNode nextNode;
    private DoubleNode prevNode;

    // Constructor
    public DoubleNode(Object data) {
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
    public DoubleNode getNextNode() {
        return nextNode;
    }

    // Setter for nextNode
    public void setNextNode(DoubleNode nextNode) {
        this.nextNode = nextNode;
    }

    // Getter for prevNode
    public DoubleNode getPrevNode() {
        return prevNode;
    }

    // Setter for prevNode
    public void setPrevNode(DoubleNode prevNode) {
        this.prevNode = prevNode;
    }

    @Override
    public boolean equals(Object o) {
           return ((Major) this.data).getName().trim().equalsIgnoreCase(((String) o).trim());
   }


    public int compareTo(DoubleNode o) {
        return ((Major) this.data).getName().compareToIgnoreCase(((Major) o.getData()).getName());
    }

    @Override
    public String toString() {
        return "DoubleNode [data=" + data + ", nextNode=" + nextNode + ", prevNode=" + prevNode + "]";
    }
}
