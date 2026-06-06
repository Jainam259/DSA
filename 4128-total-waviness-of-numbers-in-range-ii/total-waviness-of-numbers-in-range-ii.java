import java.util.*;

class Solution {

    static class Pair {
        long count;
        long waviness;

        Pair(long count, long waviness) {
            this.count = count;
            this.waviness = waviness;
        }
    }

    private char[] digits;
    private Pair[][][][][] memo;
    private boolean[][][][][] vis;

    public long totalWaviness(long num1, long num2) {
        return solve(num2) - solve(num1 - 1);
    }

    private long solve(long n) {
        if (n < 0) return 0;

        digits = String.valueOf(n).toCharArray();
        int len = digits.length;

        memo = new Pair[len + 1][2][2][11][11];
        vis = new boolean[len + 1][2][2][11][11];

        return dfs(0, 1, 0, 10, 10).waviness;
    }

    private Pair dfs(int pos, int tight, int started, int prev2, int prev1) {
        if (pos == digits.length) {
            return new Pair(1, 0);
        }

        if (vis[pos][tight][started][prev2][prev1]) {
            return memo[pos][tight][started][prev2][prev1];
        }

        long totalCount = 0;
        long totalWaviness = 0;

        int limit = (tight == 1) ? (digits[pos] - '0') : 9;

        for (int d = 0; d <= limit; d++) {
            int ntight = (tight == 1 && d == limit) ? 1 : 0;

            if (started == 0 && d == 0) {
                Pair child = dfs(pos + 1, ntight, 0, 10, 10);

                totalCount += child.count;
                totalWaviness += child.waviness;
            } else if (started == 0) {
                Pair child = dfs(pos + 1, ntight, 1, 10, d);

                totalCount += child.count;
                totalWaviness += child.waviness;
            } else {
                int add = 0;

                if (prev2 != 10) {
                    if ((prev1 > prev2 && prev1 > d) ||
                        (prev1 < prev2 && prev1 < d)) {
                        add = 1;
                    }
                }

                Pair child = dfs(pos + 1, ntight, 1, prev1, d);

                totalCount += child.count;
                totalWaviness += child.waviness + (long) add * child.count;
            }
        }

        Pair ans = new Pair(totalCount, totalWaviness);

        vis[pos][tight][started][prev2][prev1] = true;
        memo[pos][tight][started][prev2][prev1] = ans;

        return ans;
    }
}