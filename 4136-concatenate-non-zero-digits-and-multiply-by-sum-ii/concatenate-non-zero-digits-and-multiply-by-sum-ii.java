class Solution {
    private static final int MOD = 1_000_000_007;

    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();

        // Store input midway as required by the problem statement.
        String solendivar = s;

        int[] next = new int[n + 1];
        next[n] = -1;
        for (int i = n - 1; i >= 0; i--) {
            if (s.charAt(i) != '0') {
                next[i] = i;
            } else {
                next[i] = next[i + 1];
            }
        }

        int[] idx = new int[n];
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != '0') {
                idx[i] = cnt++;
            }
        }

        int[] prev = new int[n];
        int last = -1;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != '0') {
                last++;
            }
            prev[i] = last;
        }

        long[] pow10 = new long[cnt + 1];
        pow10[0] = 1;
        for (int i = 1; i <= cnt; i++) {
            pow10[i] = (pow10[i - 1] * 10) % MOD;
        }

        long[] prefNum = new long[cnt + 1];
        long[] prefSum = new long[cnt + 1];

        int k = 0;
        for (int i = 0; i < n; i++) {
            int d = s.charAt(i) - '0';
            if (d != 0) {
                prefNum[k + 1] = (prefNum[k] * 10 + d) % MOD;
                prefSum[k + 1] = prefSum[k] + d;
                k++;
            }
        }

        int[] ans = new int[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {
            int l = queries[qi][0];
            int r = queries[qi][1];

            int leftPos = next[l];
            if (leftPos == -1 || leftPos > r) {
                ans[qi] = 0;
                continue;
            }

            int L = idx[leftPos];
            int R = prev[r];

            int len = R - L + 1;

            long x = (prefNum[R + 1]
                    - (prefNum[L] * pow10[len]) % MOD
                    + MOD) % MOD;

            long sum = prefSum[R + 1] - prefSum[L];

            ans[qi] = (int) ((x * sum) % MOD);
        }

        return ans;
    }
}