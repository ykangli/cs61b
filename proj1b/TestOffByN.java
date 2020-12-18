import org.junit.Test;
import org.junit.Assert.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestOffByN {
    @Test
    public void testOffByN() {
        boolean actual1 = new OffByN(5).equalChars('a', 'f');
        assertTrue(actual1);

        boolean actual2 = new OffByN(6).equalChars('u', 'o');
        assertTrue(actual2);

        boolean actual3 = new OffByN(5).equalChars('%', '&');
        assertFalse(actual3);
    }
}
