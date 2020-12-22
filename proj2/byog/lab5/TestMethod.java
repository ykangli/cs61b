package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;


public class TestMethod {
    @Test
    public void testTopRight() {
        HexWorld.Position a = HexWorld.topRight(new HexWorld.Position(0, 0), 3);
        int actualX = a.x;
        int actualY = a.y;
        int expectedX = 5;
        int expectedY = 3;
        assertEquals(actualX, expectedX);
        assertEquals(actualY, expectedY);
    }
}
