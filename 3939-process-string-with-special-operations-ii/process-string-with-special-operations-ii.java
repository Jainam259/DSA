class Solution {
    public char processStr(String s, long k) {
        int n = s.length();
        long[] len = new long[n + 1];

        for (int i = 0; i < n; i++) {
            char ch = s.charAt(i);
            long cur = len[i];

            if (ch >= 'a' && ch <= 'z') {
                len[i + 1] = cur + 1;
            } else if (ch == '*') {
                len[i + 1] = Math.max(0, cur - 1);
            } else if (ch == '#') {
                len[i + 1] = cur * 2;
            } else if (ch == '%') {
                len[i + 1] = cur;
            }
        }

        if (k < 0 || k >= len[n]) {
            return '.';
        }

        for (int i = n - 1; i >= 0; i--) {
            char ch = s.charAt(i);
            long before = len[i];

            if (ch >= 'a' && ch <= 'z') {
                if (k == before) {
                    return ch;
                }
            } else if (ch == '#') {
                if (k >= before) {
                    k -= before;
                }
            } else if (ch == '%') {
                k = before - 1 - k;
            }
        }

        return '.';
    }
}