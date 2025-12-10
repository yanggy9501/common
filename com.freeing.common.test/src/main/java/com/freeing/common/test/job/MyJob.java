package com.freeing.common.test.job;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MyJob implements Runnable {

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
            if (!check()) {
                ServiceMain.scheduledThreadPoolExecutor.schedule(new RetryJob(0, 10, this), 1, TimeUnit.SECONDS);
            } else {
                doService();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean check() {
        System.out.println("=====  检查 =====");
        return new Random().nextInt(20) == 2;
    }

    public void doService() {
        System.out.println("------------- 执行业务 -------------");
    }
}
