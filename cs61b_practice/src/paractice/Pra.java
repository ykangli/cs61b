package paractice;

import java.util.Arrays;

public class Pra {
    public static void checkIfZero(int x) throws Exception {
        if (x == 0) {
            throw new Exception("x was zero!");
        }
        System.out.println(x); // PRINT STATEMENT
    }

    public static int mystery(int x) {
        int counter = 0;
        try {
            while (true) {
                x = x / 2;
                checkIfZero(x);
                counter += 1;
                System.out.println("counter is " + counter); // PRINT STATEMENT
            }
        } catch(Exception e) {
            return counter;
        }
    }

    public static void main(String[] args) {
        String a = "s";
        String b = "s";
        System.out.println(a.equals(b));
        System.out.println(a.hashCode());
        System.out.println(b.hashCode());
        System.out.println("_____________");
        int[] c = {1};
        int[] d = {1};
        System.out.println(Arrays.equals(c, d));
        System.out.println(Arrays.hashCode(c));
        System.out.println(Arrays.hashCode(d));
    }
}
