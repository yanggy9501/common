package com.freeing.test.thread;

import java.util.concurrent.RecursiveTask;

/**
 * @author yanggy
 */
public class MyForkJoinTask extends RecursiveTask<Long> {
    static final int SEQUENTIAL_THRESHOLD = 10000;
    int low;

    int high;

    int[] array;

    public MyForkJoinTask(int[] array, int low, int high) {
        this.low = low;
        this.high = high;
        this.array = array;
    }

    @Override
    protected Long compute() {
        //当任务拆分到小于等于阀值时开始求
        if (high - low <= SEQUENTIAL_THRESHOLD) {
             long sum = 0;
             for (int i = low; i < high; ++i) {
                 sum += array[i];
                 }
            return sum;
        } else {
            // 拆分任务
            int mid = low + (high - low) / 2;
            MyForkJoinTask left = new MyForkJoinTask(array, low, mid);
            MyForkJoinTask right = new MyForkJoinTask(array, mid, high);
            // 提交任务
            left.fork();
            right.fork();
            //获取任务的执行结果,将阻塞当前线程直到对应的子任务完成运行并返回结果
            long rightAns = right.join();
            long leftAns = left.join();
            return leftAns + rightAns;
        }
    }
}
