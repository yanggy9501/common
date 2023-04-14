package com.freeing.test.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author yanggy
 */
public class MyThreadPoolReactive {

    public static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
}
