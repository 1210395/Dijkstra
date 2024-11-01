package com.example.jaddijstra;

import javafx.geometry.Pos;

import java.util.Objects;

public class Node  {
    @Override
	public String toString() {
		return "Node [data=" + data + ", nextNode=" + nextNode + "]";
	}
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
        if(this.data instanceof CityNode)
        return ((CityNode)this.data).getCity().trim().equalsIgnoreCase(((String)o).trim());
        else
            return ((Pointer)this.data).getCityNode().getCity().trim().equalsIgnoreCase(((String)o).trim());

    }

    public int compareTo(Node o) {
        return ((CityNode)this.data).getCity().compareTo(((CityNode)o.getData()).getCity());

    }

}
