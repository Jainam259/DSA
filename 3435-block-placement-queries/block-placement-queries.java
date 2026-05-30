import java.util.*;

class Solution {

    class SegmentTree {
        int n;
        int[] tree;

        SegmentTree(int n) {
            this.n = n;
            tree = new int[4 * n];
        }

        void update(int node, int l, int r, int idx, int val) {
            if (l == r) {
                tree[node] = val;
                return;
            }

            int mid = (l + r) >> 1;

            if (idx <= mid) {
                update(node * 2, l, mid, idx, val);
            } else {
                update(node * 2 + 1, mid + 1, r, idx, val);
            }

            tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]);
        }

        void update(int idx, int val) {
            update(1, 0, n - 1, idx, val);
        }

        int query(int node, int l, int r, int ql, int qr) {
            if (ql > r || qr < l) return 0;
            if (ql <= l && r <= qr) return tree[node];

            int mid = (l + r) >> 1;
            return Math.max(
                query(node * 2, l, mid, ql, qr),
                query(node * 2 + 1, mid + 1, r, ql, qr)
            );
        }

        int query(int l, int r) {
            if (l > r) return 0;
            return query(1, 0, n - 1, l, r);
        }
    }

    public List<Boolean> getResults(int[][] queries) {
        TreeSet<Integer> coordsSet = new TreeSet<>();
        coordsSet.add(0);

        for (int[] q : queries) {
            coordsSet.add(q[1]);
        }

        List<Integer> coords = new ArrayList<>(coordsSet);
        Map<Integer, Integer> index = new HashMap<>();

        for (int i = 0; i < coords.size(); i++) {
            index.put(coords.get(i), i);
        }

        SegmentTree segTree = new SegmentTree(coords.size());

        TreeSet<Integer> obstacles = new TreeSet<>();
        obstacles.add(0);

        List<Boolean> answer = new ArrayList<>();

        for (int[] q : queries) {

            if (q[0] == 1) {
                int x = q[1];

                Integer left = obstacles.lower(x);
                Integer right = obstacles.higher(x);

                obstacles.add(x);

                // gap from left obstacle to x
                segTree.update(index.get(x), x - left);

                // if there is an obstacle on the right,
                // its gap changes from (right-left) to (right-x)
                if (right != null) {
                    segTree.update(index.get(right), right - x);
                }

            } else {
                int x = q[1];
                int sz = q[2];

                Integer prev = obstacles.floor(x);

                int maxGap = 0;

                int idxPrev = index.get(prev);
                maxGap = segTree.query(0, idxPrev);

                // last free segment from prev obstacle to x
                maxGap = Math.max(maxGap, x - prev);

                answer.add(maxGap >= sz);
            }
        }

        return answer;
    }
}