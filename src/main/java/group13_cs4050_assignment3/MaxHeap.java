package group13_cs4050_assignment3;

import javafx.scene.paint.Color;

public class MaxHeap<T extends Comparable<T>> extends Heap<T> {
    @Override
    protected void heapifyUp(int index) {
        while (index > 0 && heap.get(index).compareTo(heap.get(getParentIndex(index))) > 0) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    @Override
    protected void heapifyDown(int index) {
        int largest = index;
        int left = getLeftChildIndex(index);
        int right = getRightChildIndex(index);

        if (left < heap.size() && heap.get(left).compareTo(heap.get(largest)) > 0) {
            largest = left;
        }
        if (right < heap.size() && heap.get(right).compareTo(heap.get(largest)) > 0) {
            largest = right;
        }

        if (largest != index) {
            swap(index, largest);
            heapifyDown(largest);
        }
    }

    @Override
    public String type() {
        return "MaxHeap";
    }

    @Override
    public Color color() {
        return Color.BLUE;
    }
}

