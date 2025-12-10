package com.freeing.common.test.job;

import java.util.concurrent.TimeUnit;

public class RetryJob implements Runnable {
    int cnt = 0;
    int total = 0;
    MyJob job;
    public RetryJob(int cnt, int total, MyJob job) {
        this.cnt = cnt;
        this.total = total;
        this.job = job;
    }

    @Override
    public void run() {
        System.out.println(cnt + " : " + total);
        if (cnt == total) {
            System.out.println("重设结束：" + cnt);
            return;
        }
        System.out.println("重设：" + (cnt + 1));
        if (!job.check()) {
            ServiceMain.scheduledThreadPoolExecutor.schedule(new RetryJob(cnt + 1, 10, job), 1, TimeUnit.SECONDS);
        } else {
            job.doService();
        }
    }
}
