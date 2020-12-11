public class SLList {
    private class IntNode {
        public int item;
        public IntNode next;
        public IntNode(int item, IntNode next) {
            this.item = item;
            this.next = next;
        }
    }
    private IntNode first; //初始为空
    private int size;

    public SLList(int x) {
        first = new IntNode(x, null);
        size = 1;
    }

    public void addFirst(int x) {
        first = new IntNode(x, first);
        size += 1;
    }  

    public int size() {
        return size;
    }

    // Implement SLList.insert which takes in an integer x and inserts it at the given
    // position. If the position is after the end of the list, insert the new node at the end.
    // For example, if the SLList is 5 → 6 → 2, insert(10, 1) results in 5 → 10 → 6 → 2.

    //当SLList为空或者插入位置为0时，直接利用addFirst插入即可；
    //当插入位置是1时，可直接插入；当位置大于1时，需要设立一个指针指向当前要插入位置的前一个结点，
    //然后执行插入操作
    public void insert(int item, int position) {
        if (first == null || position == 0) {
            addFirst(item);
            return;
        }
        IntNode curr = first;
        while (position > 1 && curr.next != null) {
            position--;
            curr = curr.next;
        }
        IntNode newNode = new IntNode(item, curr.next);  
        curr.next = newNode;   
    }

    // Add another method to the SLList class that reverses the elements. Do this using
    // the existing IntNodes (you should not use new).
    public void reverse() {
        IntNode frontOfReversed = null;
        IntNode nextNodeToAdd = first;
        while (nextNodeToAdd != null) {
            IntNode remainderOfOriginal = nextNodeToAdd.next;
            nextNodeToAdd.next = frontOfReversed;
            frontOfReversed = nextNodeToAdd;
            nextNodeToAdd = remainderOfOriginal;
        }
        first = frontOfReversed;
    }
}