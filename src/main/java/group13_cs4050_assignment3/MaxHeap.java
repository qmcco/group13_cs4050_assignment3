package group13_cs4050_assignment3;

import javafx.scene.paint.Color;

public class MaxHeap<T extends Comparable<T>> extends Heap<T> {

    // Moves Elements up the heap if necessary to maintain the max-heap
    // This should be called after inserting a new element
    @Override
    protected void heapifyUp(int index) {
        // Swaps the element with its parent if it is larger than the parent
        while (index > 0 && heap.get(index).compareTo(heap.get(getParentIndex(index))) > 0) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    // Moves Elements down the heap if necessary to maintain the max-heap property
    // This should be called after removing the root element
    @Override
    protected void heapifyDown(int index) {
        int largest = index;
        int left = getLeftChildIndex(index);
        int right = getRightChildIndex(index);

        // Check left child for being larger
        if (left < heap.size() && heap.get(left).compareTo(heap.get(largest)) > 0) {
            largest = left;
        }
        // Check right child for being larger
        if (right < heap.size() && heap.get(right).compareTo(heap.get(largest)) > 0) {
            largest = right;
        }
        // If the largest needs to be changed, swap and recurse down the heap
        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    // Returns the type as a string
    @Override
    public String type() {
        return "MaxHeap";
    }

    // Overrides the color to make the nodes a different color than the others
    @Override
    public Color color() {
        return Color.BLUE;
    }
}

