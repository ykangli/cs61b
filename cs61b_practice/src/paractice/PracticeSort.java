package paractice;

import java.util.HashSet;

public class PracticeSort {
    public static boolean findSum(int[] A, int x) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A.length; j++) {
                if (A[i] + A[j] == x) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean findSumFaster(int[] A, int x) {
        int left = 0;
        int right = A.length - 1;
        while (left < right) {
            if (A[left] == A[right]) {
                return true;
            } else if (A[left] + A[right] < x) {
                left++;
            } else {
                right--;
            }
        }
        return false;
    }

    public static int[] intersection(int[] A, int[] B) {
        HashSet<Integer> set = new HashSet<>();
        for (int a : A) {
            set.add(a);
        }

        for (int b : B) {
            set.add(b);
        }

        int[] unionArray = new int[set.size()];
        int index = 0;
        for (int num : set) {
            unionArray[index] = num;
        }
        return unionArray;
    }

    public static int[] intersection2(int[] A, int[] B) {
        HashSet<Integer> setOfA = new HashSet<>();
        HashSet<Integer> integerHashSet = new HashSet<>();
        for (int num : A) {
            setOfA.add(num);
        }
        for (int num : B) {
            if (setOfA.contains(num)) {
                integerHashSet.add(num);
            }
        }
        int[] array = new int[integerHashSet.size()];
        int index = 0;
        for (int num : integerHashSet) {
            array[index] = num;
            index += 1;
        }
        return array;
    }
}
