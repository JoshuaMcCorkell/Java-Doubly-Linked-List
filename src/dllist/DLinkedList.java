package dllist;

import java.util.AbstractList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Deque;
import java.util.Iterator;

public class DLinkedList<T>
        extends AbstractList<T>
        implements Cloneable, Deque<T>
{

    private static class Node<E> {
        E val;
        Node<E> next;
        Node<E> prev;

        Node(E element, Node<E> nextNode, Node<E> prevNode) {
            this.val = element;
            this.next = nextNode;
            this.prev = prevNode;
        }

        Node(E element) {
            this.val = element;
            this.next = null;
            this.prev = null;
        }
    }

    int length;
    Node<T> head;
    Node<T> tail;

    @Override
    public T get(int index) {
        return getNode(index).val;
    }

    /**
     * Gets the element at the specified position, starting from the end (tail) of
     * the list.
     * This method assumes that the index is in bounds.
     * 
     * @param index The index of the element to retrieve.
     * @return The element.
     * @throws NullPointerException if the index is out of bounds.
     */
    T getFromEnd(int index) {
        return getNodeFEnd(index).val;
    }

    /**
     * Gets the element at the specified position, starting from the start (head) of
     * the list.
     * This method assumes that the index is in bounds.
     * 
     * @param index The index of the element to retrieve.
     * @return The element.
     * @throws NullPointerException if the index is out of bounds.
     */
    T getFromStart(int index) {
        return getNodeFStart(index).val;
    }

    @Override
    public int size() {
        return length;
    }

    @Override
    public boolean add(T element) {
        linkLast(element);
        return true;
    }

    @Override
    public void add(int index, T element) {
        validateIndex(index, true);
        linkAfter(element, getNode(index - 1));
    }

    /**
     * Sets the given element as the head of the list.
     * In other words, adds the element at the start of the list.
     * This method will increment the length of the list.
     * 
     * @param element The element to add to the start of the list.
     */
    void linkFirst(T element) {
        if (length == 0) {
            setHead(element);
        } else if (length == 1) {
            Node<T> newNode = new Node<>(element, head, null);
            head.prev = newNode;
            head = newNode;
            tail = newNode.next;
        } else {
            Node<T> newNode = new Node<>(element, head, null);
            head.prev = newNode;
            head = newNode;
        }
        length++;
    }

    /**
     * Sets the given element as the tail of the list.
     * In other words, adds the element at the end of the list.
     * Note: If the length of the list is 0, the tail will NOT be set, only the
     * head.
     * This method will increment the length of the list.
     * 
     * @param element The element to add to the end of the list.
     */
    void linkLast(T element) {
        if (length == 0) {
            setHead(element);
        } else if (length == 1) {
            tail = new Node<>(element, null, head);
            head.next = tail;
        } else {
            Node<T> newNode = new Node<>(element, null, tail);
            tail.next = newNode;
            tail = newNode;
        }
        length++;
    }

    /**
     * Unlinks the head of the list. (The first element)
     */
    void unlinkFirst() {
        if (length == 1) {
            clear();
        } else if (length == 2) {
            head = tail;
            tail = null;
            head.next = null;
            head.prev = null;
            length = 1;
        } else {
            head.next.prev = null;
            head = head.next;
            length--;
        }
    }

    /**
     * Unlinks the tail of the list. (The last element)
     */
    void unlinkLast() {
        if (length == 1) {
            clear();
        } else if (length == 2) {
            head.next = null;
            tail = null;
            length = 1;
        } else {
            tail = tail.prev;
            tail.next = null;
            length--;
        }
    }

    /**
     * Inserts the given element in a node AFTER the given node.
     * This method will increment the length of the list.
     * 
     * @param element  The element to insert.
     * @param prevNode The node to insert the element after.
     */
    void linkAfter(T element, Node<T> prevNode) {
        if (prevNode == tail || (prevNode == head && tail == null)) {
            linkLast(element);
        } else {
            Node<T> newNode = new Node<>(element, prevNode.next, prevNode);
            prevNode.next.prev = newNode;
            prevNode.next = newNode;
            length++;
        }
    }

    /**
     * Gets the node at the given index.
     * This method assumes that the index is in the list.
     * 
     * @param index The Node to get.
     * @return The node at position {@code index}.
     */
    Node<T> getNode(int index) {
        if (index > length / 2) {
            return getNodeFEnd(index);
        } else {
            return getNodeFStart(index);
        }
    }

    /**
     * Gets the node at the given index, starting from the start of the list.
     * This method assumes that index is in the list.
     * 
     * @param index The Node to get.
     * @return The node at position {@code index}.
     * @throws NullPointerException if the index is out of bounds.
     */
    Node<T> getNodeFStart(int index) {
        var pointer = head;
        for (int i = 0; i < index; i++) {
            pointer = pointer.next;
        }
        return pointer;
    }

    /**
     * Gets the node at the given index, starting from the end of the list.
     * This method assumes that index is in the list.
     * 
     * @param index The Node to get.
     * @return The node at position {@code index}.
     * @throws NullPointerException if the index is out of bounds.
     */
    Node<T> getNodeFEnd(int index) {
        var pointer = tail;
        for (int i = 0; i < length - index - 1; i++) {
            pointer = pointer.prev;
        }
        return pointer;
    }

    /**
     * Sets the head to a new node containing the given element.
     * This method assumes the head is NOT already set. The next and prev of the new
     * head
     * will be null.'
     * NOTE: This method does NOT increment the length of the list.
     * 
     * @param element The element to set in the head node.
     */
    void setHead(T element) {
        head = new Node<>(element);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c.isEmpty()) {
            return false;
        }
        for (var element : c) {
            add(element);
        }
        return true;
    }

    /**
     * Adds all the elements in the specified array to the end of the list,
     * in the order that they are given in that array.
     * 
     * @param c The array to add elements from.
     * @return true if the list changed as a result of the call, otherwise false.
     */
    public boolean addAll(T[] c) {
        if (c.length == 0) {
            return false;
        }
        for (var element : c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c.isEmpty()) {
            return false;
        }
        if (head == null) {
            addAll(c);
            return true;
        }
        var pointer = getNode(index - 1);
        for (var element : c) {
            linkAfter(element, pointer);
            pointer = pointer.next;
        }
        return true;
    }

    /**
     * Inserts all the elements in the array at the specified position,
     * in the order that they are given in that array.
     * 
     * @param c The array to add elements from.
     * @return true if the list changed as a result of the call, otherwise false.
     */
    public boolean addAll(int index, T[] c) {
        if (c.length == 0) {
            return false;
        }
        if (head == null) {
            addAll(c);
            return true;
        }
        var pointer = getNode(index - 1);
        for (var element : c) {
            linkAfter(element, pointer);
            pointer = pointer.next;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }

        ListIterator<T> e1 = listIterator();
        ListIterator<?> e2 = ((List<?>) o).listIterator();
        while (e1.hasNext() && e2.hasNext()) {
            T o1 = e1.next();
            Object o2 = e2.next();
            if (!(o1 == null ? o2 == null : o1.equals(o2)))
                return false;
        }
        return !(e1.hasNext() || e2.hasNext());
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        for (T e : this)
            hashCode = 31 * hashCode + (e == null ? 0 : e.hashCode());
        return hashCode;
    }

    void validateIndex(int index, boolean includeEnd) {
        if (!(index < length || (index == length && includeEnd))) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + length);
        }
    }

    @Override
    public void clear() {
        for (Node<T> pointer = head; pointer != null;) {
            Node<T> next = pointer.next;
            pointer.val = null;
            pointer.next = null;
            pointer.prev = null;
            pointer = next;
        }
        length = 0;
        head = tail = null;
    }

    @Override
    public boolean isEmpty() {
        return length == 0;
    }

    void checkNotNull(T e) {
        if (e == null) {
            throw new NullPointerException("Can't add null to a Deque.");
        }
    }

    @Override
    public void addFirst(T e) {
        checkNotNull(e);
        linkFirst(e);
    }

    @Override
    public void addLast(T e) {
        checkNotNull(e);
        linkLast(e);
    }

    @Override
    public boolean offerFirst(T e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(T e) {
        addLast(e);
        return true;
    }

    @Override
    public T removeFirst() {
        if (length == 0) {
            throw new NoSuchElementException("This Deque is empty");
        }
        T value = head.val;
        unlinkFirst();
        return value;
    }

    @Override
    public T removeLast() {
        if (length == 0) {
            throw new NoSuchElementException("This Deque is empty");
        }
        T value;
        if (length == 1) {
            value = head.val;
        } else {
            if (tail == null) {
                throw new NullPointerException("length is " + length + " null pointer exception because tail is null.");
            }
            value = tail.val;
        }
        unlinkLast();
        return value;
    }

    @Override
    public T pollFirst() {
        if (length == 0) {
            return null;
        }
        return removeFirst();
    }

    @Override
    public T pollLast() {
        if (length == 0) {
            return null;
        }
        if (length == 1) {
            return removeFirst();
        }
        return removeLast();
    }

    @Override
    public T getFirst() {
        return head.val;
    }

    @Override
    public T getLast() {
        return tail.val;
    }

    @Override
    public T peekFirst() {
        if (length == 0) {
            return null;
        }
        return head.val;
    }

    @Override
    public T peekLast() {
        if (length == 0) {
            return null;
        } 
        if (length == 1) {
            return head.val;
        }
        return tail.val;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean offer(T e) {
        offerLast(e);
        return true;
    }

    @Override
    public T remove() {
        return removeFirst();
    }

    @Override
    public T poll() {
        return pollFirst();
    }

    @Override
    public T element() {
        return head.val;
    }

    @Override
    public T peek() {
        return peekFirst();
    }

    @Override
    public void push(T e) {
        addFirst(e);
    }

    @Override
    public T pop() {
        return removeFirst();
    }

    @Override
    public Iterator<T> descendingIterator() {
        return new Iterator<T>() {
            int currentIndex = length - 1;
            Node<T> pointer = tail;

            @Override
            public boolean hasNext() {
                return currentIndex >= 0;
            }

            @Override
            public T next() {
                if (this.hasNext()) {
                    var value = pointer.val;
                    pointer = pointer.prev;
                    currentIndex--;
                    return value;
                } else {
                    throw new NoSuchElementException("No more elements in this iterator");
                }
                
            }
        };
    }

    @Override 
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int currentIndex = 0;
            Node<T> pointer = head;

            @Override
            public boolean hasNext() {
                return currentIndex < length;
            }

            @Override
            public T next() {
                if (this.hasNext()) {
                    var value = pointer.val;
                    pointer = pointer.next;
                    currentIndex++;
                    return value;
                } else {
                    throw new NoSuchElementException("No more elements in this iterator");
                }
            }
        };
    }
}