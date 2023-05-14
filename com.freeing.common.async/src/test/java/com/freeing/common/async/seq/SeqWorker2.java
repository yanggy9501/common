package com.freeing.common.async.seq;

import com.freeing.common.async.action.ICallback;
import com.freeing.common.async.action.IWorker;
import com.freeing.common.async.worker.ResultState;
import com.freeing.common.async.worker.WorkResult;
import com.freeing.common.async.wrapper.WorkerWrapper;

import java.util.Map;

/**
 * @author wuweifeng wrote on 2019-11-20.
 */
public class SeqWorker2 implements IWorker<String, String>, ICallback<String, String> {

    @Override
    public String action(String object, Map<String, WorkerWrapper<?, ?>> allWrappers) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("result = " + "---param = " + object + " from 1");
        return "result = " + "---param = " + object + " from 0";
    }

    @Override
    public String defaultValue() {
        return "worker2--default";
    }

    @Override
    public void beforeAction() {
        System.out.println(Thread.currentThread().getName() + "- start --" + System.currentTimeMillis());
    }

    @Override
    public void afterFinish(String param, WorkResult<String> workResult) {
        if (workResult.getResultState() == ResultState.SUCCESS) {
            System.out.println("callback worker2 success--" +  "----" + workResult.getResult()
                    + "-threadName:" +Thread.currentThread().getName());
        } else {
            System.err.println("callback worker2 failure--" +  "----"  + workResult.getResult()
                    + "-threadName:" +Thread.currentThread().getName());
        }
    }

}
