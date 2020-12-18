import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    /**
     * This test should randomly output some items and add it into deque,
     * then confirm if item located in StudentDeque is same as ArrayDequeSolution
     */
    public void testDeque() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();

        //Using addFirst
        for (int i = 0; i < 100; i++) {
            int randomNum = StdRandom.uniform(99);
            ads.addFirst(randomNum);
            sad.addFirst(randomNum);
        }

        for (int i = 0; i < 100; i++) {
            int actual1 = sad.get(i);
            int expected1 = ads.get(i);
            assertEquals(actual1, expected1);
        }

        //Using addLast
        for (int i = 0; i < 100; i++) {
            int randomNum = StdRandom.uniform(99);
            ads.addLast(randomNum);
            sad.addLast(randomNum);
        }

        for (int i = 0; i < 100; i++) {
            int actual2 = sad.get(i);
            int expected2 = ads.get(i);
            assertEquals(actual2, expected2);
        }
    }
}
