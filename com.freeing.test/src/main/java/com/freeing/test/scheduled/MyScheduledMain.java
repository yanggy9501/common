package com.freeing.test.scheduled;

import java.util.concurrent.TimeUnit;

/**
 * @author yanggy
 */
public class MyScheduledMain {
    public static void main(String[] args) {
        MyScheduledTask task = new MyScheduledTask();
        MyThreadPoolReactive.scheduledExecutorService.schedule(task, 2, TimeUnit.SECONDS);
    }
}
