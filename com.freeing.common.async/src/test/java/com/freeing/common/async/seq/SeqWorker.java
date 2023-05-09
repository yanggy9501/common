package com.freeing.common.async.seq;

import com.freeing.common.async.action.ICallback;
import com.freeing.common.async.action.IWorker;
import com.freeing.common.async.worker.ResultState;
import com.freeing.common.async.worker.WorkResult;
import com.freeing.common.async.wrapper.WorkerWrapper;

import java.util.Map;


public class SeqWorker implements IWorker<String, String>, ICallback<String, String> {

    @Override
    public String action(String object, Map<String, WorkerWrapper<?, ?>> allWrappers) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "result = " + "---param = " + object + " from 0";
    }


    @Override
    public String defaultValue() {
        return "worker0--default";
    }

    @Override
    public void beforeStart() {
        //System.out.println(Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
    }

    @Override
    public void afterFinish(String param, WorkResult<String> workResult) {
        if (workResult.getResultState() == ResultState.SUCCESS) {
            System.out.println("callback worker0 success--" + "----" + workResult.getResult()
                    + "-threadName:" +Thread.currentThread().getName());
        } else {
            System.err.println("callback worker0 failure--"  + "----"  + workResult.getResult()
                    + "-threadName:" +Thread.currentThread().getName());
        }
    }
}
