package com.freeing.common.async.seq;

import com.freeing.common.async.Async;
import com.freeing.common.async.wrapper.WorkerWrapper;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 串行测试
 * @author wuweifeng wrote on 2019-11-20.
 */
public class TestSequential {
    public static void main(String[] args) throws InterruptedException, ExecutionException {


        SeqWorker w = new SeqWorker();
        SeqWorker1 w1 = new SeqWorker1();
        SeqWorker2 w2 = new SeqWorker2();

        //顺序0-1-2
        WorkerWrapper<String, String> workerWrapper2 =  new WorkerWrapper.Builder<String, String>()
                .worker(w2)
                .callback(w2)
                .param("2")
                .build();

        WorkerWrapper<String, String> workerWrapper1 =  new WorkerWrapper.Builder<String, String>()
                .worker(w1)
                .callback(w1)
                .param("1")
                .next(workerWrapper2)
                .build();

        WorkerWrapper<String, String> workerWrapper =  new WorkerWrapper.Builder<String, String>()
                .worker(w)
                .callback(w)
                .param("0")
                .next(workerWrapper1)
                .build();

        testGroupTimeout(workerWrapper);
    }

    private static void testGroupTimeout(WorkerWrapper<String, String> workerWrapper) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Async.beginWork(120000, Arrays.asList(workerWrapper));
    }
}
