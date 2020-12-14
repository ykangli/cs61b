public class ArrayDeque<T> {
    /**
     * 传统循环队列设置队头和队尾两个指针front和rear，初始式front和rear指向同一位置。判空条件为front == rear；
     * 当front == rear时虽然队满，但和判空条件重复，无法判断是队空还是队满。
     * 为了判断队列是否为满，一般有两种方法：1.设置flag 2.浪费一个空间，当front的下一个位置是rear时，就认为队列已满，即为
     * front = (rear + 1) % lengthOfArray
     * <p>
     * 这里设计两个指针nextFirst，nextLast分别表示队头插入和队尾插入的位置
     * <p>
     * 什么时候deque已满？何时需要扩容？何时需要压缩容量以满足25%使用率？判断条件是什么？？
     * 答：当 size == items.length时，认为deque为满，此时需要扩容。当执行remove操作前需判断数组使用率是否小于25%，
     * 若使用率小于25%，需要先压缩数组，再进行remove操作
     * 当进行add和remove操作后，修改使用率ratio，以便随时跟踪，动态调整数组大小
     * <p>
     * 与传统的循环队列当队列已满不能再入队不同，这里设计的队列可以动态调整大小，即使此时队列已满，
     * 可利用resize()来调整数组容量，使得可以继续添加元素
     * 利用 oneMinus()找到下一次执行addFirst()的位置， onePlus()找到下一次执行addLast()的位置
     *
     * @rules： add and remove must take constant time, except during resizing operations.
     * @rules： get and size must take constant time.
     * @rules： The starting size of your array should be 8.
     * @rules： The amount of memory that your program uses at any given time must be proportional to the number of items.
     * For example, if you add 10,000 items to the deque, and then remove 9,999 items,
     * you shouldn’t still be using an array of length 10,000ish.
     * For arrays of length 16 or more, your usage factor should always be at least 25%. For smaller arrays,
     * your usage factor can be arbitrarily low.
     * 该规则表明，数组长度需要进行动态调整，以满足当数组长度大于16时，使用率不小于25%
     */
    private T[] items;
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
        items = (T[]) new Object[initLength];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
        ratio = 0;
    }

    /**
     * Compute the index immediately “before” a given index.
     */
    public int minusOne(int index) {
        if (index == 0) {
            return items.length - 1;
        }
        return index - 1;
    }

    /**
     * Compute the index immediately “after” a given index.
     */
    public int plusOne(int index) {
        return (index + 1) % items.length;
    }

    /**在动态调整数组过程中，将原数组复制到新数组中，原数组最后一个元素的复制位置的确定，需要利用此函数*/
    public int plusOne(int index, int length) {
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
        T[] newItems = (T[]) new Object[capacity];
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
        T lastToCopy = items[currentLast];
        items = newItems;
        items[nextLast] = lastToCopy;
        nextLast = plusOne(nextLast);
        ratio = (double) size / items.length;
    }

    /**
     * Adds an item of type General to the front of the deque.
     */
    public void addFirst(T item) {
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
    public void addLast(T item) {
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
    public T removeFirst() {
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
        T removed = items[currentFirst];
        nextFirst = currentFirst;
        size -= 1;
        ratio = (double) size / items.length;
        System.out.println("removeFirst的位置是： " + currentFirst + "; removeFirst的元素是： " + removed);
        return removed;
    }

    /**
     * Removes and returns the item at the back of the deque. If no such item exists, returns null.
     */
    public T removeLast() {
        if (size == 0) {
            System.out.println("队列为空不能进行remov操作");
            return null;
        }
        if (ratio < usageRatio && items.length >= minLength) {
            contract();
        }
        //nextFirst向后移动一个位置，找到deque的队头
        int currentLast = minusOne(nextLast);
        T removed = items[currentLast];
        nextLast = currentLast;
        size -= 1;
        ratio = (double) size / items.length;
        System.out.println("removeLast的位置是： " + currentLast + "; removeLast的元素是： " + removed);
        return removed;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null. Must not alter the deque!
     */
    public T get(int index) {
        if (size == 0) {
            System.out.println("队列为空不能进行remov操作");
            return null;
        }
        int position = (nextFirst + 1) + index;
        return items[position];
    }
}
