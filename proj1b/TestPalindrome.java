import org.junit.Test;

import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        boolean a = palindrome.isPalindrome("abcdcba");
        assertTrue(a);

        boolean b = palindrome.isPalindrome("asdrczok");
        assertFalse(b);

        boolean c = palindrome.isPalindrome("");
        assertTrue(c);
    }

    @Test
    public void testIsPalindromeOverload() {
        OffByOne offByOne = new OffByOne();

        boolean a = palindrome.isPalindrome("abcdcba", offByOne);
        assertFalse(a);

        boolean b = palindrome.isPalindrome("abydzcb", offByOne);
        assertTrue(b);
    }
}
