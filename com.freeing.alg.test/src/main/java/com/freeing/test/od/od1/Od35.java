package com.freeing.test.od.od1;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * https://blog.csdn.net/misayaaaaa/article/details/127947829
 *
 * @author yanggy
 */
public class Od35 {
    /*
100,200,300,400,500

     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] split = scanner.nextLine().split(",");
        int[] array = Arrays.stream(split).mapToInt(Integer::parseInt).toArray();
        System.out.println(diskNum(array));
    }

    public static int diskNum(int[] nums) {
        // 装箱问题：大的 + 小的最接近目标
        // 遍历所有的 nums，每遍历一个将其装入一个 disk 中，如果原来就有能装下该 num的就用他
        // 注意如果有 100 100 400 400 重小到大遍历，肯定时 100 + 100 组合到一起了，所有不能这么遍历，所有从大到小遍历
        // 用一个集合来保存已经使用过的 disk，为了保证 大的 + 小的，所有，重大到小遍历 + 优先级队列保存已经使用的disk
        Arrays.sort(nums);
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Integer::compare);
        // 遍历：从大到小
        for (int i = nums.length - 1; i >=0 ; i--) {
            if (priorityQueue.isEmpty()) {
                // add 一块 disk
                priorityQueue.add(nums[i]);
            } else {
                // 从已经使用的 disk 中获取一个，判断当前能否加入到其中，否则在加一块，取最大还是最小呢，当然是最小的，因为遍历时从大到小，所以
                // 当前队列中都是比当前大的，更小的后面遍历
                if (500 - priorityQueue.peek() >= nums[i]) {
                    // 和到当前磁盘
                    int size = priorityQueue.poll();
                    priorityQueue.offer(size + nums[i]);
                } else {
                    priorityQueue.add(nums[i]);
                }
            }
        }
        return 1;
    }
}
