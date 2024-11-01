package com.example.jaddijstra;

public class MinHeapOne {
    private CityNode[] heap;
    private int size;
    private int capacity;

    public MinHeapOne(int capacity) {
        this.capacity = capacity;
        this.heap = new CityNode[capacity];
        this.size = 0;
    }

    private int getParentIndex(int index) { return (index - 1) / 2; }
    private int getLeftChildIndex(int index) { return 2 * index + 1; }
    private int getRightChildIndex(int index) { return 2 * index + 2; }

    private boolean hasLeftChild(int index) { return getLeftChildIndex(index) < size; }
    private boolean hasRightChild(int index) { return getRightChildIndex(index) < size; }
    private boolean hasParent(int index) { return getParentIndex(index) >= 0; }

    private CityNode leftChild(int index) { return heap[getLeftChildIndex(index)]; }
    private CityNode rightChild(int index) { return heap[getRightChildIndex(index)]; }
    private CityNode parent(int index) { return heap[getParentIndex(index)]; }

    private void swap(int indexOne, int indexTwo) {
        CityNode temp = heap[indexOne];
        heap[indexOne] = heap[indexTwo];
        heap[indexTwo] = temp;
    }

    private void ensureCapacity() {
        if (size == capacity) {
            capacity *= 2;
            CityNode[] newHeap = new CityNode[capacity];
            System.arraycopy(heap, 0, newHeap, 0, size);
            heap = newHeap;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(CityNode node) {
        ensureCapacity();
        heap[size] = node;
        size++;
        heapifyUp();
    }

    public CityNode extractMin() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");
        CityNode min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapifyDown();
        return min;
    }

    public CityNode peek() {
        if (isEmpty()) throw new IllegalStateException("Heap is empty");
        return heap[0];
    }

    private void heapifyUp() {
        int index = size - 1;
        while (hasParent(index) && parent(index).getDistance() > heap[index].getDistance()) {
            swap(getParentIndex(index), index);
            index = getParentIndex(index);
        }
    }

    private void heapifyDown() {
        int index = 0;
        while (hasLeftChild(index)) {
            int smallerChildIndex = getLeftChildIndex(index);
            if (hasRightChild(index) && rightChild(index).getDistance() < leftChild(index).getDistance()) {
                smallerChildIndex = getRightChildIndex(index);
            }

            if (heap[index].getDistance() < heap[smallerChildIndex].getDistance()) {
                break;
            } else {
                swap(index, smallerChildIndex);
            }
            index = smallerChildIndex;
        }
    }
}
