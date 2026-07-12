import java.util.*;

class Solution {
    public int[] arrayRankTransform(int[] arr) {
        int n = arr.length;
        if (n == 0) return arr;

        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        Map<Integer, Integer> rankMap = new HashMap<>();
        int rank = 1;

        for (int num : sorted) {
            if (!rankMap.containsKey(num)) {
                rankMap.put(num, rank++);
            }
        }

        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = rankMap.get(arr[i]);
        }

        return result;
    }
}