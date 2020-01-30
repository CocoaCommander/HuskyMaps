package priorityqueues;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T extends Comparable<T>> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 1;
    List<PriorityNode<T>> items;
    // TODO: add fields as necessary

    public ArrayHeapMinPQ() {
        items = new ArrayList<>();
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.
    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Adds an item with the given priority value.
     * Runs in O(log N) time (except when resizing).
     * @throws IllegalArgumentException if item is null or is already present in the PQ
     */
    @Override
    public void add(T item, double priority) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns true if the PQ contains the given item; false otherwise.
     * Runs in O(log N) time.
     */
    @Override
    public boolean contains(T item) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns the item with the least-valued priority.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T peekMin() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Removes and returns the item with the least-valued priority.
     * Runs in O(log N) time (except when resizing).
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T removeMin() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Changes the priority of the given item.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the item is not present in the PQ
     */
    @Override
    public void changePriority(T item, double priority) {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Returns the number of items in the PQ.
     * Runs in O(log N) time.
     */
    @Override
    public int size() {
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
