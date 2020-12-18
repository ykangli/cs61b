public class ArrayDeque<Item> implements Deque<Item>{
    private Item[] items;
    private int size; // size of Arraydeque
    private int nextFirst; // position of executing addFirst
    private int nextLast; // position of executing addLast
    private static int initLength = 8; // The starting length of array
    private static int eFactor = 2; //expand factor
    private static int cFactor = 2; //contract factor
    private static int minLength = 16; // For arrays of length 16 or more, usage factor should  be at least 25%.
    private static double usageRatio = 0.25;
    private double ratio;


    /**
     * Creates an empty linked list deque.
     */
    public ArrayDeque() {
        items = (Item[]) new Object[initLength];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
        ratio = 0;
    }

    /**
     * Compute the index immediately “before” a given index.
     */
    private int minusOne(int index) {
        if (index == 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    /**
     * Compute the index immediately “after” a given index.
     */
    private int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /**在动态调整数组过程中，将原数组复制到新数组中，原数组最后一个元素的复制位置的确定，需要利用此函数*/
    private int plusOne(int index, int length) {
        return (index + 1) % length;
    }

    /**
     * expand length of array
     */
    private void expand() {
        int newLength = items.length * eFactor;
        //注意：动态调整数组后，ratio需要改变
        resize(newLength);
    }

    /**
     * contract length of array
     */
    private void contract() {
        int newLength = items.length / cFactor;
        //注意：动态调整数组后，ratio需要改变
        resize(newLength);
    }

    /**
     * Resizing Arrays
     */
    private void resize(int capacity) {
        //1.调整数组长度，既要考虑队列满扩容，也要考虑队列使用率小于25%时数组压缩的问题
        //2.调整ratio
        //将原数组的元素复制到新数组后，要确定新的nextFirst和nextLast

        //创建一个新的数组，长度为调整后的长度capacity，将原数组复制到新数组
        Item[] newItems = (Item[]) new Object[capacity];
        //计算出当前队头，队尾所在的位置
        int currentFirst = plusOne(nextFirst);
        int currentLast = minusOne(nextLast);
        nextFirst = 0;
        nextLast = 1;
        //遍历整个Arraydeque，将原数组元素复制到新数组
        for (int i = currentFirst; i != currentLast; i = plusOne(i)) {
            newItems[nextLast] = items[i];
            nextLast = plusOne(nextLast);
        }
        //原数组最后一个元素计算位置时，应该利用新数组的长度
        nextLast = minusOne(nextLast);
        nextLast = plusOne(nextLast, newItems.length);
        Item lastToCopy = items[currentLast];
        items = newItems;
        items[nextLast] = lastToCopy;
        nextLast = plusOne(nextLast);
        ratio = (double) size / items.length;
    }

    /**
     * Adds an item of type General to the front of the deque.
     */
    @Override
    public void addFirst(Item item) {
        //判断队列是否已满，若已满，先扩容，后执行入队操作
        if (size == items.length) {
            expand();
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
        ratio = (double) size / items.length;
    }

    /**
     * Adds an item of type General to the back of the deque.
     */
    @Override
    public void addLast(Item item) {
        //判断队列是否已满，若已满，先扩容，后执行入队操作
        if (size == items.length) {
            expand();
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
        ratio = (double) size / items.length;
    }

    /**
     * Returns true if deque is empty, false otherwise.
     */
    @Override
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
    @Override
    public void printDeque() {
        int currentFirst = plusOne(nextFirst);
        int currentLast = minusOne(nextLast);
        //遍历整个Arraydeque
        for (int i = currentFirst; i != currentLast; i = plusOne(i)) {
            System.out.print(items[i].toString() + " ");
            nextLast = plusOne(nextLast);
        }
        System.out.print(items[currentLast]);
    }

    /**
     * Removes and returns the item at the front of the deque. If no such item exists, returns null.
     * 注意：remove操作不是将该位置的数据删除，即置为null，而是移动 nextFirst 或 nextLst 指针，
     * 在此位置进行add操作时会将这里的元素覆盖。
     */
    @Override
    public Item removeFirst() {
        //先remove再压缩，还是先压缩再remove，涉及到ratio的问题.........？？？
        //当数组长度大于等于16，且使用率小于25%时，对数组进行压缩以满足使用率
        if (size == 0) {
            return null;
        }
        if (ratio < usageRatio && items.length >= minLength) {
            contract();
        }
        //nextFirst向后移动一个位置，找到deque的队头
        int currentFirst = plusOne(nextFirst);
        Item removed = items[currentFirst];
        nextFirst = currentFirst;
        size -= 1;
        ratio = (double) size / items.length;
        return removed;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     */
    @Override
    public Item removeLast() {
        if (size == 0) {
            return null;
        }
        if (ratio < usageRatio && items.length >= minLength) {
            contract();
        }
        //nextFirst向后移动一个位置，找到deque的队头
        int currentLast = minusOne(nextLast);
        Item removed = items[currentLast];
        nextLast = currentLast;
        size -= 1;
        ratio = (double) size / items.length;
        return removed;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    @Override
    public Item get(int index) {
        if (size == 0) {
            return null;
        }
        int position = (plusOne(nextFirst) + index) % items.length;
        return items[position];
    }
}
