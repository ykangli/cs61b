package paractice;

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
    // 需要有一个nextNodeToAdd，指向当前要插入的结点；需要有一个reminderOfOriginal始终指向原SLList
    public void reverse() {
        IntNode frontOfReversed = null; //反转后的SLList
        IntNode nextNodeToAdd = first; //当前要插入反转SLList的结点
        while (nextNodeToAdd != null) {
            IntNode remainderOfOriginal = nextNodeToAdd.next; //原SSList中剩余要插入的
            nextNodeToAdd.next = frontOfReversed;
            frontOfReversed = nextNodeToAdd; //这两步为将结点插入反转SLList的操作
            nextNodeToAdd = remainderOfOriginal; //更新要插入的节点
        }
        first = frontOfReversed;
    }

    public void reverseDemo() {
        IntNode reverseList = null; //reverseList指向当前要插入的节点
        IntNode curr =  first; //当前结点初始指向第一个结点
        while (curr != null) {
            IntNode reminderOfList = curr.next; //保证不断链，能找到下一个要插入的结点
            curr.next = reverseList; //头插操作
            reverseList = curr; //头插操作
            curr = reminderOfList; //更新当前要插入的结点
        }
        first = reverseList;
    }

    public void reverseRecursive() {
        first = reverseHelper(first);
    }

    public IntNode reverseHelper(IntNode L) {
        if (L == null || L.next == null) {
            return L;
        } else {
            IntNode reversed = reverseHelper(L.next); //将原链表中，后面部分反转，然后将原来前面的部分尾插在反转后的链表后面
            reversed.next = L; //尾插操作
            L.next = null; //将反转后的链表最后指向null
            return reversed;
        }
    }

    public static void main(String[] args) {
        SLList L = new SLList(5);
        L.addFirst(4);
        L.addFirst(3);
        L.addFirst(2);
        L.addFirst(1);
        L.reverse();
    }
}
