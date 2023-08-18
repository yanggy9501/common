package com.freeing.test.od;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        Class<Main> mainClass = Main.class;
        System.out.println(mainClass.getName());
        System.out.println(mainClass.getSimpleName());
    }

    public static int diskNum(int[] nums){
        Arrays.sort(nums);
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i = nums.length-1; i >= 0; i--) {
            if (queue.isEmpty()){
                queue.offer(nums[i]);
            }else {
                if ((500-queue.peek())>=nums[i]){
                    int size = queue.poll();
                    queue.offer(size+nums[i]);
                }else {
                    queue.offer(nums[i]);
                }
            }
        }
        return queue.size();
    }
}