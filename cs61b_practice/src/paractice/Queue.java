package paractice;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * Using a Stack class which implements the stack ADT to achieve Queue class.
 */
public class Queue<E> {
    private Stack<E> stack = new Stack();
    Deque<String> deque = new ArrayDeque<>();

    public void push(E element) {
        stack.push(element);
    }

    /** Using iterative */
    public E pop() {
        Stack<E> temp = new Stack();
        while (!stack.isEmpty()) {
            temp.push(stack.pop());
        }
        E topPop = temp.pop();
        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }
        return topPop;
    }
}
