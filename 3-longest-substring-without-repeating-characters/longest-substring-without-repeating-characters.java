class Solution {
    public int lengthOfLongestSubstring(String s) {
        int maxlen = 0;
        int n = s.length();

        for (int i = 0; i < n; i++) {
            int[] hash = new int[256];

            for (int j = i; j < n; j++) {
                char ch = s.charAt(j);
                if (hash[ch] == 1)
                    break;

                hash[ch] = 1;
                int length = j - i + 1;
                maxlen = Math.max(length, maxlen);
            }
        }
        return maxlen;
    }
}