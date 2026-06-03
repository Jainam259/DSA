class Solution {
    public int earliestFinishTime(
            int[] landStartTime,
            int[] landDuration,
            int[] waterStartTime,
            int[] waterDuration) {

        long minLandFinish = Long.MAX_VALUE;
        for (int i = 0; i < landStartTime.length; i++) {
            minLandFinish = Math.min(
                minLandFinish,
                (long) landStartTime[i] + landDuration[i]
            );
        }

        long minWaterFinish = Long.MAX_VALUE;
        for (int j = 0; j < waterStartTime.length; j++) {
            minWaterFinish = Math.min(
                minWaterFinish,
                (long) waterStartTime[j] + waterDuration[j]
            );
        }

        long ans = Long.MAX_VALUE;

        // Land -> Water
        for (int j = 0; j < waterStartTime.length; j++) {
            long finish =
                (long) waterDuration[j]
                + Math.max(minLandFinish, (long) waterStartTime[j]);

            ans = Math.min(ans, finish);
        }

        // Water -> Land
        for (int i = 0; i < landStartTime.length; i++) {
            long finish =
                (long) landDuration[i]
                + Math.max(minWaterFinish, (long) landStartTime[i]);

            ans = Math.min(ans, finish);
        }

        return (int) ans;
    }
}