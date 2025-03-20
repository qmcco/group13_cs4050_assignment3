package group13_cs4050_assignment3;

import javafx.scene.paint.Color;

public class MinHeap<T extends Comparable<T>> extends Heap<T> {

    // Moves Elements Up the heap if necessary to maintain the min-heap
    // This should be called after inserting a new element
    @Override
    protected void heapifyUp(int index) {
        // Swaps the element with its parent if it is smaller than the parent
        while (index > 0 && heap.get(index).compareTo(heap.get(getParentIndex(index))) < 0) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    // Moves Elements Down the heap if necessary to maintain the min-heap
    // This should be called after removing the root element
    @Override
    protected void heapifyDown(int index) {
        int smallest = index;
        int left = getLeftChildIndex(index);
        int right = getRightChildIndex(index);

        // Check left child for being smaller
        if (left < heap.size() && heap.get(left).compareTo(heap.get(smallest)) < 0) {
            smallest = left;
        }
        // Check right child for being smaller
        if (right < heap.size() && heap.get(right).compareTo(heap.get(smallest)) < 0) {
            smallest = right;
        }
        // If the smallest needs to be changed then it swaps and recurses down the heap
        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }
    // Returns the type as a string
    @Override
    public String type() {
        return "MinHeap";
    }
    // Overrides the color to make the nodes a different color than the others
    @Override
    public Color color() {
        return Color.GREEN;
    }
}
