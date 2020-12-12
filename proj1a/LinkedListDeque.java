public class LinkedListDeque<T> {
    private class TNode {
        public TNode prev;
        public T item;
        public TNode next;

        public TNode(TNode prev, T item, TNode next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    /**
     * sentinel.prev always points to last node
     */
    private TNode sentinel;
    private int size;

    /**
     * Creates an empty linked list deque.
     */
    public LinkedListDeque() {
        sentinel = new TNode(null, (T) "sentinel", null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    public LinkedListDeque(T item) {
        sentinel = new TNode(null, (T) "sentinel", null);
        sentinel.next = new TNode(sentinel, item, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    /**
     * Adds an item of type General to the front of the deque.
     */
    public void addFirst(T item) {
        sentinel.next = new TNode(sentinel, item, sentinel.next);
        // 当从空LinkedListDeque的前面插入结点时，保证sentinel.prev指向指向最后一个结点;
        // 当在非空情况下在LinkedListDeque前面插入结点时，sentinel.prev无需改变，只有进行addLast操作时才需要
        sentinel.next.next.prev = sentinel.next;
        size += 1;
    }

    /**
     * Adds an item of type General to the back of the deque.
     */
    public void addLast(T item) {
        sentinel.prev.next = new TNode(sentinel.prev, item, sentinel);
        sentinel.prev = sentinel.prev.next;
        size += 1;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns the number of items in the deque.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {
        TNode p = sentinel.next;
        while (p != sentinel) {
            System.out.println(p.item + " ");
            p = p.next;
        }
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T item = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return item;
    }


    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return item;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        int n = 0;
        TNode p = sentinel.next;
        if (index >= size) {
            return null;
        }
        while (p != sentinel) {
            if (n == index) {
                return p.item;
            }
            n++;
            p = p.next;
        }
        return null;
    }

    /**
     * Same as get, but uses recursion.
     */
    public T getRecursive(int index) {
        if (sentinel.next == sentinel) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    public T getRecursiveHelper(int index, TNode start) {
        if (index == 0) {
            return sentinel.next.item;
        }
        return  getRecursiveHelper(index - 1, sentinel.next.next);
    }
}
