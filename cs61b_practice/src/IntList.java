public class IntList {
    public int first;
    public IntList rest;

    public IntList(int first, IntList rest) {
        this.first = first;
        this.rest = rest;
    }

    /** IntList A = IntList.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);IntList B = IntList.list(9, 8, 7, 6, 5, 4, 3, 2, 1);
     * After calling A.skippify(), A: (1, 3, 6, 10)
     *  After calling B.skippify(), B: (9, 7, 4)
     */
    public void skippify() {
        //当前结点p
        IntList p = this;
        int n = 1;
        while (p != null) {
            IntList next = p.rest; //next保证不断链
            //利用for循环计算需要向后跳多少步
            for (int i = 0; i < n; i++) {
                if (next == null) {
                    break;
                }
                next = next.rest;
            }
            p.rest = next;
            p = p.rest; //当前结点向后移动
            n++;
        }
    }

    /** For example given 1 -> 2 -> 2 -> 2 -> 3,
     * Mutate it to become 1 -> 2 -> 3 (destructively)
     */
    public static void removeDuplicates(IntList p) {
        if (p == null) {
            return;
        }
        //两个指针一前一后，判断是否相等
        IntList current = p.rest;
        IntList previous = p;
        while (current != null) {
            if (current.first == previous.first) {
                previous.rest = current.rest;
            } else {
                previous = current;
            }
            current = current.rest;
        }
    }

    public static void main(String[] args) {
        IntList L = new IntList(6, null);
        L = new IntList(5, L);
        L = new IntList(4, L);
        L = new IntList(3, L);
        L = new IntList(2, L);
        L = new IntList(1, L);

        L.skippify();
    }
}
