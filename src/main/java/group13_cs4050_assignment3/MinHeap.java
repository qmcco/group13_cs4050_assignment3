package group13_cs4050_assignment3;

import javafx.scene.paint.Color;

public class MinHeap<T extends Comparable<T>> extends Heap<T> {

    @Override
    protected void heapifyUp(int index) {
        while (index > 0 && heap.get(index).compareTo(heap.get(getParentIndex(index))) < 0) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    @Override
    protected void heapifyDown(int index) {
        int smallest = index;
        int left = getLeftChildIndex(index);
        int right = getRightChildIndex(index);

        if (left < heap.size() && heap.get(left).compareTo(heap.get(smallest)) < 0) {
            smallest = left;
        }
        if (right < heap.size() && heap.get(right).compareTo(heap.get(smallest)) < 0) {
            smallest = right;
        }

        if (smallest != index) {
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    @Override
    public String type() {
        return "MinHeap";
    }

    @Override
    public Color color() {
        return Color.GREEN;
    }
}
