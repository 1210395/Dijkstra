package com.example.structure2025;

public class DoubleLinkedList {
    private DoubleNode first, last;
    private int count = 0;

    // Constructor
    public DoubleLinkedList() {
    }

    // Getter for the first node
    public DoubleNode getFirstNode() {
        return first;
    }

    // Method to get the node at the specified index
    public DoubleNode get(int index) {
        if (index < 0 || index >= count) {
            return null; // Index out of bounds
        }
        DoubleNode current = first;
        for (int i = 0; i < index; i++) {
            current = current.getNextNode();
        }
        return current; // Return the node at the specified index
    }

    // Method to find a node with a specific element
    public DoubleNode find(Object element) {
        DoubleNode current = first;
        while (current != null) {
            if (current.equals(element)) {
                return current;
            }
            current = current.getNextNode();
        }
        return null; // Element not found
    }

    // Method to add an element in a sorted manner
    public void addSorted(Object element) {
        DoubleNode newNode = new DoubleNode(element);
        if (count == 0 || newNode.compareTo(first) <= 0) {
            addFirst(element); // Add to the beginning if the list is empty or new element is smaller than the first
            return;
        }
        if (newNode.compareTo(last) >= 0) {
            addLast(element); // Add to the end if new element is greater than the last
            return;
        }

        DoubleNode current = first;
        while (current.getNextNode() != null && newNode.compareTo(current.getNextNode()) > 0) {
            current = current.getNextNode(); // Move to the next node until finding the right position
        }
        newNode.setNextNode(current.getNextNode());
        newNode.setPrevNode(current);
        current.getNextNode().setPrevNode(newNode);
        current.setNextNode(newNode);
        count++;
    }

    // Method to add an element at the beginning of the list
    public void addFirst(Object element) {
        DoubleNode newNode = new DoubleNode(element);
        if (first == null) {
            first = last = newNode;
        } else {
            newNode.setNextNode(first);
            first.setPrevNode(newNode);
            first = newNode;
        }
        count++;
    }

    // Method to add an element at the end of the list
    public void addLast(Object element) {
        DoubleNode newNode = new DoubleNode(element);
        if (count == 0) {
            first = last = newNode;
        } else {
            last.setNextNode(newNode);
            newNode.setPrevNode(last);
            last = newNode;
        }
        count++;
    }

    // Method to remove the first element of the list
    public Object removeFirst() {
        if (count == 0) {
            return null;
        }
        DoubleNode temp = first;
        if (count == 1) {
            first = last = null;
        } else {
            first = first.getNextNode();
            first.setPrevNode(null);
        }
        count--;
        return temp.getData();
    }

    // Method to remove the last element of the list
    public Object removeLast() {
        if (count == 0) {
            return null;
        }
        DoubleNode temp = last;
        if (count == 1) {
            first = last = null;
        } else {
            last = last.getPrevNode();
            last.setNextNode(null);
        }
        count--;
        return temp.getData();
    }
    public boolean isValidStudent(String id){
        for(int i=0;i<this.size();i++){
            Node current=((Major)this.get(i).getData()).getList().getFirstNode();
            while(current!=null){
                if(((Student)current.getData()).getId().equalsIgnoreCase(id.trim()))
                    return false;
                current=current.getNextNode();
            }
        }
        return true;
    }
    // Method to remove the element at a specific index
    public Object remove(int index) {
        if (index < 0 || index >= count) {
            return false;
        }
        if (index == 0) {
            return removeFirst();
        }
        if (index == count - 1) {
            return removeLast();
        }
        DoubleNode current = first;
        for (int i = 0; i < index - 1; i++) {
            current = current.getNextNode();
        }
        DoubleNode temp = current.getNextNode();
        current.setNextNode(temp.getNextNode());
        if (temp.getNextNode() != null) {
            temp.getNextNode().setPrevNode(current);
        }
        count--;
        return temp.getData();
    }

    // Method to remove a specific element from the list
    public boolean remove(Object element) {
        if (count == 0) {
            return false;
        }
        if (((Major)first.getData()).equals(element)) {
            removeFirst();
            return true;
        }
        DoubleNode current = first;
        while (current != null && !current.getData().equals(element)) {
            current = current.getNextNode();
        }
        if (current == null) {
            return false;
        }
        current.getPrevNode().setNextNode(current.getNextNode());
        if (current.getNextNode() != null) {
            current.getNextNode().setPrevNode(current.getPrevNode());
        }
        count--;
        return true;
    }

    // Method to get the size of the list
    public int size() {
        return count;
    }

    // Method to check if the list is empty
    public boolean isEmpty() {
        return count == 0;
    }

    // Method to print the list
    public void printList() {
        DoubleNode current = first;
        while (current != null) {
            System.out.println(((Major)(current.getData())).toString()+"\n");
           ((Major)(current.getData())).getList().printList();

            current = current.getNextNode();
        }
    }
}
