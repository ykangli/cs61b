public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            Character character = word.charAt(i);
            deque.addLast(character);
        }
        return deque;
    }

    /** Any word of length 1 or 0 is a palindrome */
    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return isPalindromeHelper(deque);
    }

    private boolean isPalindromeHelper(Deque<Character> deque) {
        if (deque.size() == 0 || deque.size() == 1) {
            return true;
        } else {
            Character first = deque.removeFirst();
            Character last = deque.removeLast();
            if (first.compareTo(last) != 0) {
                return false;
            }
            isPalindromeHelper(deque);
        }
        return true;
    }

    /** 接口用来描述类应该做什么，而不是具体怎么去实现
     * 在 Java 8 之前，接口绝不会实现方法
     * 虽然不能构造接口对象，但可以声明接口变量，即 cc 是接口变量。
     * 尽管 Character对象没有状态，还是需要建立这个对象的一个实例，需要一个对象来调用equalsChars方法
     * 与一个类实现 Comparable接口来进行比较的方法不同
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        return isPalindromeHelper(deque, cc);
    }

    private boolean isPalindromeHelper(Deque<Character> deque, CharacterComparator cc) {
        if (deque.size() == 0 || deque.size() == 1) {
            return true;
        } else {
            Character first = deque.removeFirst();
            Character last = deque.removeLast();
            if (cc.equalChars(first, last)) {
                return isPalindromeHelper(deque, cc);
            } else {
                return false;
            }
        }
    }
}
