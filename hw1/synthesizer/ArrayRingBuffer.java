package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.rb = (T[]) new Object[capacity];
        this.first = 0;
        this.last = 0;
        this.fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        if (this.fillCount == this.capacity) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        this.fillCount += 1;
        last = (last + 1) % this.capacity;
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        if (this.fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T deletedItem = rb[first];
        this.fillCount -= 1;
        first = (first + 1) % this.capacity;
        return deletedItem;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        if (this.fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }

    private class ArrayRingBufferIterator implements Iterator<T> {
        private int wizpos;

        ArrayRingBufferIterator() {
            wizpos = first;
        }

        @Override
        public boolean hasNext() {
            if (fillCount == 0) {
                return false;
            }
            int pos = wizpos;
            if (pos != last) {
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            T returnItem = rb[wizpos];
            wizpos = (wizpos + 1) % capacity;
            return returnItem;
        }
    }

//    public static void main(String[] args) {
//        ArrayRingBuffer arb = new ArrayRingBuffer(10);
//        arb.enqueue(1);
//        arb.enqueue(2);
//        arb.enqueue(3);
//        arb.enqueue(4);
//        arb.enqueue(5);
//        arb.enqueue(6);
//        for (Object item : arb) {
//            System.out.println(item);
//        }
//    }
}
