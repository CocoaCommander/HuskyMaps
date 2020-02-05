package priorityqueues;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.HashMap;

public class ArrayHeapMinPQ<T extends Comparable<T>> implements ExtrinsicMinPQ<T> {
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    static final int START_INDEX = 1;
    List<PriorityNode<T>> items;
    private int size;
    private HashMap<T, Integer> allItems;

    public ArrayHeapMinPQ() {
        this.items = new ArrayList<>();
        this.items.add(null);
        this.size = 0;
        this.allItems = new HashMap<T, Integer>();
    }

    // Here's a method stub that may be useful. Feel free to change or remove it, if you wish.
    // You'll probably want to add more helper methods like this one to make your code easier to read.

    /**
     * A helper method for swapping the items at two indices of the array heap.
     */
    private void swap(int a, int b) {
        PriorityNode<T> temp = items.get(a);
        items.set(a, items.get(b));
        items.set(b, temp);
    }

    private boolean compare(int thisPriority, int thatPriority) {
        return items.get(thisPriority).getPriority() > items.get(thatPriority).getPriority();
    }

    /**
     * Adds an item with the given priority value.
     * Runs in O(log N) time (except when resizing).
     *
     * @throws IllegalArgumentException if item is null or is already present in the PQ
     */
    @Override
    public void add(T item, double priority) {
        if (this.contains(item) || item == null) {
            throw new IllegalArgumentException("Item can not be null or already in the Priority Queue");
        }
        PriorityNode<T> toBack = new PriorityNode<>(item, priority);
        size++;
        items.add(size, toBack);
        allItems.put(item, percolateUp(size));
    }

    private int percolateUp(int back) {
        while (back > 1 && compare(back / 2, back)) {
            swap(back, back / 2);
            back = back / 2;
        }
        return back;
    }

    /**
     * Returns true if the PQ contains the given item; false otherwise.
     * Runs in O(log N) time.
     */
    @Override
    public boolean contains(T item) {
        return allItems.containsKey(item);
    }

    /**
     * Returns the item with the least-valued priority.
     * Runs in O(log N) time.
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T peekMin() {
        if (size == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return items.get(START_INDEX).getItem();
    }

    /**
     * Removes and returns the item with the least-valued priority.
     * Runs in O(log N) time (except when resizing).
     * @throws NoSuchElementException if the PQ is empty
     */
    @Override
    public T removeMin() {
        if (size == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        allItems.remove(items.get(START_INDEX).getItem());
        T min = items.get(START_INDEX).getItem();
        items.set(START_INDEX, items.get(size));
        items.remove(size);
        size--;
        int newIndex = percolateDown(START_INDEX);
        if (size != 0) {
            allItems.replace(items.get(newIndex).getItem(), newIndex);
        }
        return min;
    }

    private int percolateDown(int current) {
        while (2 * current <= size) {
            int down = 2 * current;
            if (down < size && compare(down, down + 1)) {
                down++;
            }
            if (!compare(current, down)) {
                break;
            }
            swap(current, down);
            current = down;
        }
        return current;
    }

    /**
     * Changes the priority of the given item.
     * Runs in O(log N) time.
     *
     * @throws NoSuchElementException if the item is not present in the PQ
     */
    @Override
    public void changePriority(T item, double priority) {
        if (!this.contains(item)) {
            throw new NoSuchElementException("Item is not in PQ");
        }
        int index = allItems.get(item);
        int newIndex = index;
        PriorityNode<T> toChange = items.get(index);
        if (toChange.getPriority() != priority) {
            toChange.setPriority(priority);
            items.set(index, toChange);
            if (index == 1 || compare(index, index / 2)) {
                newIndex = percolateDown(index);
            } else if (compare(index / 2, index)) {
                newIndex = percolateUp(index);
            }
        }
        allItems.replace(item, newIndex);
    }

    /**
     * Returns the number of items in the PQ.
     * Runs in O(log N) time.
     */
    @Override
    public int size() {
        return size;
    }
}
