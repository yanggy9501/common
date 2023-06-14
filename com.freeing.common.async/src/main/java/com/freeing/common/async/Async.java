package com.freeing.common.async;

import com.freeing.common.async.wrapper.WorkerWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yanggy
 */
public class Async {
    private static final Logger log = LoggerFactory.getLogger(Async.class);
    /**
     * 创建线程池:
     * 核心线程数：计算机内核数 / 2
     * 最大线程数：计算机内核数
     * 空闲时间：60s，超过60s超过核心线程数的空闲线程被杀死
     * 任务队列长度：200
     * 线程池工厂：使用了jdk默认工厂
     * handler（队列满时的任务拒绝策略）：让提交任务的线程去执行
     */
    private static volatile ExecutorService COMMON_POOL;

    public static boolean beginWork(long timeout, ExecutorService executorService,
        List<WorkerWrapper<?, ?>> workerWrappers) throws ExecutionException, InterruptedException {
        if (workerWrappers == null || workerWrappers.isEmpty()) {
            return false;
        }
        // 定义一个map，存放所有的wrapper，key为wrapper的唯一id，value是该wrapper，可以从value中获取wrapper的result
        Map<String, WorkerWrapper<?, ?>> forAllUsedWrappers = new ConcurrentHashMap<>();
        CompletableFuture<?>[] futures = new CompletableFuture[workerWrappers.size()];
        for (int i = 0; i < workerWrappers.size(); i++) {
            WorkerWrapper<?, ?> workerWrapper = workerWrappers.get(i);
            futures[i] = CompletableFuture.runAsync(() ->
                workerWrapper.work(executorService, timeout, forAllUsedWrappers), executorService);
        }

        try {
            CompletableFuture.allOf(futures).get(timeout, TimeUnit.MILLISECONDS);
            return true;
        } catch (TimeoutException ex) {
            log.info("Timeout: ", ex);
        }
        return false;
    }

    public static boolean beginWork(long timeout, List<WorkerWrapper<?, ?>> workerWrappers)
        throws ExecutionException, InterruptedException {
        return beginWork(timeout, getCommonPool(), workerWrappers);
    }

    private static ExecutorService getCommonPool() {
        if (COMMON_POOL == null) {
            synchronized (Async.class) {
                if (COMMON_POOL == null) {
                    int processors = Runtime.getRuntime().availableProcessors();
                    COMMON_POOL = new ThreadPoolExecutor(
                        processors >> 1,
                        processors,
                        60,
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<>(200 * processors),
                        new DefaultThreadFactory(),
                        new ThreadPoolExecutor.CallerRunsPolicy()
                    );
                }
            }
        }
        return COMMON_POOL;
    }

    static class DefaultThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        DefaultThreadFactory() {
            namePrefix = "WorkerWrapper" + "-pool-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(null, r, namePrefix + threadNumber.getAndIncrement(),
                0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
}
