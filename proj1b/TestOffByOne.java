import org.junit.Test;

import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.

    /**
     * the equalChars method in the OffByOne class.
     */
    @Test
    public void testEqualsChars() {
        boolean actual1 = offByOne.equalChars('c', 'b');
        assertTrue(actual1);

        boolean actual2 = offByOne.equalChars(' ', 'b');
        assertFalse(actual2);

        boolean actual3 = offByOne.equalChars('a', 'b');
        assertTrue(actual3);

        boolean actual4 = offByOne.equalChars('&', '%');
        assertTrue(actual4);

        boolean actual5 = offByOne.equalChars('B', 'b');
        assertFalse(actual5);
    }
}
