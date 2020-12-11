import org.junit.Assert;
import org.junit.Test;

public class TestArray {
    /**
     * Test Array.isAreEqual()
     */
    @Test
    public void arrayIsAreEqual() {
        int[] a1 = {1, 2, 3, 4, 5, 6};
        int[] b1 = {1, 2, 3, 4, 5, 6};
        boolean expected1 = true;
        boolean actual1 = Array.isAreEqual(a1, b1);
        Assert.assertEquals(expected1, actual1);

    }
}
