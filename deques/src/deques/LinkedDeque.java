package deques;

public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back; // may be the same as front, if you're using circular sentinel nodes
    // TODO replace this with any additional fields you may need

    public LinkedDeque() {
        size = 0;
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    static class Node<T> {
        // IMPORTANT: Do not rename these fields or change their visibility.
        // We access these during grading to test your code.
        T value;
        Node<T> next;
        Node<T> prev;
        // TODO: replace this with any additional constructors/fields you may need

        Node(T value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    public void addFirst(T item) {
        // TODO: replace this with your code
        size += 1;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public void addLast(T item) {
        // TODO: replace this with your code
        size += 1;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        // TODO: replace this with your code
        size -= 1;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        // TODO: replace this with your code
        size -= 1;
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        // TODO: replace this with your code
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    public int size() {
        return size;
    }
}
