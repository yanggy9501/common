package com.freeing.test.scheduled;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * @author yanggy
 */
public class MyScheduledTask implements Runnable {

    @Override
    public void run() {
        System.out.println(LocalTime.now() + ":开始定时任务...");
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        MyScheduledTask task = new MyScheduledTask();
        MyThreadPoolReactive.scheduledExecutorService.schedule(task, 2, TimeUnit.SECONDS);
        System.out.println(LocalTime.now() + ":开始下一个任务...");
    }
}
