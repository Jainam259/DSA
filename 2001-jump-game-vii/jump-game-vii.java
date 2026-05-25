class Solution {
    public boolean canReach(String s, int minJump, int maxJump) {
        int n = s.length();

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);

        int farthest = 0; // Track farthest visited index

        while (!queue.isEmpty()) {
            int current = queue.poll();

            // Possible jump range
            int start = Math.max(current + minJump, farthest + 1);
            int end = Math.min(current + maxJump, n - 1);

            for (int i = start; i <= end; i++) {
                if (s.charAt(i) == '0') {
                    if (i == n - 1) {
                        return true;
                    }
                    queue.offer(i);
                }
            }

            farthest = end;
        }

        return n == 1; // If string length is 1
    }
}