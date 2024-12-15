package com.freeing.common.test.q;

import java.util.concurrent.ArrayBlockingQueue;

public class ArrayBlockingQueueTest {
     volatile int[] arr = new int[10];
     void f() {
         arr = new int[]{1, 2};
     }
    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue queue = new ArrayBlockingQueue(10);
        queue.put("");
    }

}
