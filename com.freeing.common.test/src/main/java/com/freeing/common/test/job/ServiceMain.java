package com.freeing.common.test.job;

import java.util.concurrent.ScheduledThreadPoolExecutor;

public class ServiceMain {

    public static ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);

    public static void main(String[] args) {

        MyJob myJob = new MyJob();
        myJob.run();

    }
}
