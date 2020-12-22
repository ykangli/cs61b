package paractice;

import java.util.*;

public class Test {
    public boolean twoSum(int[] A, int k) {
        Set<Integer> a = new HashSet<>();
        for (int i : A) {
            if (a.contains(k - i)) {
                return true;
            }
            a.add(i);
        }
        return false;
    }

    public static void topFivePopularWords(String[] words, int k) {
        Map<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            if (!counts.containsKey(word)) {
                counts.put(word, 1);
            } else {
                counts.put(word, counts.get(word) + 1);
            }
        }

        // PriorityQueue是优先队列，按某种顺序排列的队列。可在构造方法中添加 comparator，使其按照comparator重写的排序方式排列
        PriorityQueue<String> pq = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return counts.get(b) - counts.get(a);
            }
        });

        // 将所有键值添加到pq中
        pq.addAll(counts.keySet());

        for (int i = 0; i < k; i++) {
            System.out.println(pq.poll());
        }
    }
}
