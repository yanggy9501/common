package com.freeing.test.dynamicplan;

/**
 * 01 背包
 *
 * @author yanggy
 */
public class Pack01 {

    public static void main(String[] args) {
        int[] weights = {1, 3, 4};
        int[] values = {15, 20, 30};
        System.out.println(maxValue(4, weights, values));
    }

    /**
     * 能否取得最大值 1：当添加第 n 个物品时能否有更大的值（看当只装n - 1 个物品时的情况），取决于是否要添加该物品，添加：就要看只能装 pack - weight[i] 的情况 不添加：就要看当前的情况-动态规划算法
     * 2：添加和不添加就是一个-回溯算法
     *
     * @param pack
     * @param weights
     * @param values
     * @return
     */
    public static int maxValue(int pack, int[] weights, int[] values) {
        // 计算到pack重量即可
        int[][] dp = new int[weights.length][pack + 1];
        // dp 初始化，其实dp数组只用一行也可以
        for (int i = weights[0]; i <= pack; i++) {
            dp[0][i] = values[0];
        }

        // dp公式 dp[i][j](==有==i个物品书包承重为j的最大值) = max(不添加：dp[i-1][j], max: dp[i][j - weight[i]] + value[i]
        // 遍历顺序是添加物品的顺序
        for (int i = 1; i < weights.length; i++) {
            for (int j = 1; j <= pack; j++) {
                int mayValue = j - weights[i] >= 0 ? dp[i][j - weights[i]] + values[i] : dp[i - 1][j];
                dp[i][j] = Math.max(dp[i - 1][j], mayValue);
            }
        }

        return dp[weights.length - 1][pack];
    }

//    public static int maxValueByBackTracking(int pack, int[] weights, int[] values) {
//
//    }

}
