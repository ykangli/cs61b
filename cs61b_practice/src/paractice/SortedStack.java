package paractice;

import java.util.Stack;

public class SortedStack<Item extends Comparable<Item>> {
    private Stack<Item> a;
    private Stack<Item> b;

    public SortedStack() {
        a = new Stack<>();
        b = new Stack<>();
    }

    public void push(Item t) {
        while (!a.isEmpty() && a.peek().compareTo(t) < 0) {
            b.push(a.pop());
        }
        a.push(t);
        while (!b.isEmpty()) {
            a.push(b.pop());
        }
    }


    public Item pop() {
        return a.pop();
    }
}
