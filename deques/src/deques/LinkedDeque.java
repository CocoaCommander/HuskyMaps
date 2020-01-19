package deques;

public class LinkedDeque<T> extends AbstractDeque<T> {
    private int size;
    // IMPORTANT: Do not rename these fields or change their visibility.
    // We access these during grading to test your code.
    Node<T> front;
    Node<T> back; // may be the same as front, if you're using circular sentinel nodes

    public LinkedDeque() {
        size = 0;
        this.front = new Node<>(null);
        this.back = new Node<>(null);
        this.front.next = this.back;
        this.back.prev = this.front;
    }

    static class Node<T> {
        // IMPORTANT: Do not rename these fields or change their visibility.
        // We access these during grading to test your code.
        T value;
        Node<T> next;
        Node<T> prev;

        Node(T value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    public void addFirst(T item) {
        size += 1;
        Node<T> newNode = new Node<>(item);
        newNode.prev = this.front;
        newNode.next = this.front.next;
        this.front.next.prev = newNode;
        this.front.next = newNode;
    }

    public void addLast(T item) {
        size += 1;
        Node<T> newNode = new Node<>(item);
        newNode.next = this.back;
        newNode.prev = this.back.prev;
        this.back.prev.next = newNode;
        this.back.prev = newNode;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node<T> removedNode = this.front.next;
        this.front.next = this.front.next.next;
        this.front.next.prev = this.front;
        return removedNode.value;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        Node<T> removedNode = this.back.prev;
        this.back.prev = this.back.prev.prev;
        this.back.prev.next = this.back;
        return removedNode.value;
    }

    public T get(int index) {
        if ((index >= size) || (index < 0)) {
            return null;
        }
        Node<T> curr = this.front.next;
        for (int i = 0; i < index; i++) {
            curr = curr.next;
        }
        return curr.value;
    }

    public int size() {
        return size;
    }
}
