package com.freeing.common.async.wrapper;

import com.freeing.common.async.action.DefaultCallback;
import com.freeing.common.async.action.ICallback;
import com.freeing.common.async.action.IWorker;
import com.freeing.common.async.worker.DependWrapper;
import com.freeing.common.async.worker.ResultState;
import com.freeing.common.async.worker.WorkResult;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对每个worker及callback进行包装，一对一
 *
 * @author yanggy
 */
public class WorkerWrapper<T, V> {

    private static final int INIT = 0;
    private static final int FINISH = 1;
    private static final int ERROR = 2;
    private static final int WORKING = 3;

    /**
     * 该wrapper的唯一标识
     */
    private final String id;

    /**
     * worker将来要处理的param，即入参
     */
    private final T param;

    /**
     * 执行单元
     */
    private final IWorker<T, V> worker;

    /**
     * 执行单元的回调
     */
    private final ICallback<T, V> callback;

    /**
     * 钩子变量，用来存放临时的执行结果
     */
    private volatile WorkResult<V> workResult = WorkResult.defaultResult();

    /**
     * 标记该事件是否已经被处理过了，譬如已经超时返回 false了，后续 rpc又收到返回值了，则不再二次回调
     * 1-finish, 2-error, 3-working
     */
    private final AtomicInteger state = new AtomicInteger(0);


    /**
     * 在自己后面的wrapper，如果没有，自己就是末尾；如果有一个，就是串行；如果有多个，有几个就需要开几个线程
     */
    private List<WorkerWrapper<?, ?>> nextWrappers;

    /**
     * 依赖的wrappers，有2种情况，1:必须依赖的全部完成后，才能执行自己 2:依赖的任何一个、多个完成了，就可以执行自己
     * 通过 must字段来控制是否依赖项必须完成
     */
    private List<DependWrapper> dependWrappers;

    /**
     * 该 map存放所有wrapper的 id 和 wrapper映射，该 map 是惰性性的，一开始并不会所有任务组合中的 WorkerWrapper 都放进来.
     */
    private Map<String, WorkerWrapper<?, ?>> forAllUsedWrappers;

    public WorkerWrapper(String id, IWorker<T, V> worker, T param, ICallback<T, V> callback) {
        if (worker == null) {
            throw new NullPointerException("async worker is null");
        }
        this.worker = worker;
        this.param = param;
        this.id = id;
        //允许不设置回调
        if (callback == null) {
            callback = new DefaultCallback<>();
        }
        this.callback = callback;
    }

    private void addDepend(WorkerWrapper<?, ?> workerWrapper, boolean must) {
        addDepend(new DependWrapper(workerWrapper, must));
    }

    private void addDepend(DependWrapper dependWrapper) {
        if (dependWrappers == null) {
            dependWrappers = new ArrayList<>();
        }
        //如果依赖的是重复的同一个，就不重复添加了
        for (DependWrapper wrapper : dependWrappers) {
            if (wrapper.equals(dependWrapper)) {
                return;
            }
        }
        dependWrappers.add(dependWrapper);
    }

    private <W, C> void addNext(WorkerWrapper<W, C> workerWrapper) {
        if (nextWrappers == null) {
            nextWrappers = new ArrayList<>();
        }
        // 避免添加重复
        for (WorkerWrapper<?, ?> wrapper : nextWrappers) {
            if (workerWrapper.equals(wrapper)) {
                return;
            }
        }
        nextWrappers.add(workerWrapper);
    }

    public void work(ExecutorService executorService, WorkerWrapper<?, ?> fromWrapper, long remainTime,
            Map<String, WorkerWrapper<?, ?>> forAllUsedWrappers) {
        this.forAllUsedWrappers = forAllUsedWrappers;
        // 将自己也存放到被使用的 WorkerWrapper 集合中
        forAllUsedWrappers.put(id, this);
        // 判断是否超时，若超时则快速失败
        // 多线程执行的，假设该任务由 a 线程执行并且快成功了，b 线程刚进来并且判断超时，会怎样
        long now = System.currentTimeMillis();
        if (remainTime <= 0) {
            fastFail(INIT, null);
        }

        // 【核心：执行自己的任务】如果没有任何依赖，说明自己就是第一批要执行的
        if (dependWrappers == null || dependWrappers.size() == 0) {
            // 阻塞执行本任务
            fire();
            // 执行下个任务
        }
    }

    /**
     * 执行自己的 job.
     */
    private void fire() {
        // 阻塞取结果
        this.workResult = doWorkerJob();
    }

    private WorkResult<V> doWorkerJob() {
        // 这一步感觉多余，下面 cas 修改只会由一个线程能成功，其他都失败返回默认 workResult
        if (!isNullResult()) {
            return workResult;
        }
        try {
            // 如果已经不是 init 状态了，说明此时被执行或已执行完毕。这一步很重要，可以保证任务不被重复执行
            // 并发执行也只有一个能执行成功
            if (!compareAndSetState(INIT, WORKING)) {
                // 如果没有执行成功，返回默认的结果封装的引用进行占位
                return workResult;
            }
            // 执行任务前的回调
            callback.beforeStart();
            // 执行任务
            V resultValue = worker.action(param, forAllUsedWrappers);

            // 如果状态不是在 working，说明别的地方已经修改了，只有一种情况即控制台关闭所有任务造成状态从 WORKING 修改为其他，造成这里cas 失败
            // 任务完成
            if (!compareAndSetState(WORKING, FINISH)) {
                return workResult;
            }
            workResult.setResultState(ResultState.SUCCESS);
            workResult.setResult(resultValue);
            return workResult;
        } catch (Exception e) {
            // 这一步也感觉多余，能执行到这里必然是执行任务的时候出现异常了，任务的执行也只能由一个线程执行，其他线程都会在 cas 出返回
            if (isNullResult()) {
                return workResult;
            }
            fastFail(WORKING, e);
            return workResult;
        }
    }

    /**
     * 快速失败
     */
    private boolean fastFail(int expect, Exception e) {
        // 试图将它从expect状态，改成Error
        if (!compareAndSetState(expect, ERROR)) {
            return false;
        }
        // 尚未处理过结果
        if (isNullResult()) {
            if (e == null) {
                workResult = defaultResult();
            } else {
                workResult = defaultExResult(e);
            }
        }
        // 执行完成的回调
        callback.afterFinish(param, workResult);
        return true;
    }
    private WorkResult<V> defaultResult() {
        workResult.setResultState(ResultState.TIMEOUT);
        workResult.setResult(worker.defaultValue());
        return workResult;
    }

    private WorkResult<V> defaultExResult(Exception ex) {
        workResult.setResultState(ResultState.EXCEPTION);
        workResult.setResult(worker.defaultValue());
        workResult.setEx(ex);
        return workResult;
    }


    private boolean isNullResult() {
        return ResultState.DEFAULT == workResult.getResultState();
    }

    private boolean compareAndSetState(int expect, int update) {
        return this.state.compareAndSet(expect, update);
    }

    /**
     * WorkerWrapper 构造器
     *
     * @param <W> 入参
     * @param <C> 出参
     */
    public static class Builder<W, C> {
        /**
         * 该 wrapper的唯一标识
         */
        private String id = UUID.randomUUID().toString();

        /**
         * worker将来要处理的 param
         */
        private W param;

        private IWorker<W, C> worker;

        private ICallback<W, C> callback;

        /**
         * 自己后面的所有依赖（包含强依赖）
         */
        private List<WorkerWrapper<?, ?>> nextWrappers;

        /**
         * 自己依赖的所有
         */
        private List<DependWrapper> dependWrappers;

        /**
         * 存储强依赖于自己的 wrapper 集合，是 nextWrappers 的子集
         */
        private Set<WorkerWrapper<?, ?>> selfIsMustSet;

        public Builder<W, C> worker(IWorker<W, C> worker) {
            this.worker = worker;
            return this;
        }

        public Builder<W, C> param(W w) {
            this.param = w;
            return this;
        }

        public Builder<W, C> id(String id) {
            if (id != null) {
                this.id = id;
            }
            return this;
        }

        public Builder<W, C> callback(ICallback<W, C> callback) {
            this.callback = callback;
            return this;
        }

        public Builder<W, C> depend(WorkerWrapper<?, ?>... wrappers) {
            if (wrappers == null) {
                return this;
            }
            for (WorkerWrapper<?, ?> wrapper : wrappers) {
                depend(wrapper);
            }
            return this;
        }

        public Builder<W, C> depend(WorkerWrapper<?, ?> wrapper) {
            return depend(wrapper, true);
        }

        public Builder<W, C> depend(WorkerWrapper<?, ?> wrapper, boolean isMust) {
            if (wrapper == null) {
                return this;
            }
            // isMust 控制当前 WorkerWrapper 是否强依赖于入参 wrapper
            DependWrapper dependWrapper = new DependWrapper(wrapper, isMust);
            if (dependWrappers == null) {
                dependWrappers = new ArrayList<>();
            }
            dependWrappers.add(dependWrapper);
            return this;
        }

        public Builder<W, C> next(WorkerWrapper<?, ?> wrapper) {
            return next(wrapper, true);
        }

        public Builder<W, C> next(WorkerWrapper<?, ?> wrapper, boolean selfIsMust) {
            if (nextWrappers == null) {
                nextWrappers = new ArrayList<>();
            }
            nextWrappers.add(wrapper);
            // 下一个 WorkerWrapper 是否强依赖于自己
            if (selfIsMust) {
                if (selfIsMustSet == null) {
                    selfIsMustSet = new HashSet<>();
                }
                selfIsMustSet.add(wrapper);
            }
            return this;
        }

        public Builder<W, C> next(WorkerWrapper<?, ?>... wrappers) {
            if (wrappers == null) {
                return this;
            }
            for (WorkerWrapper<?, ?> wrapper : wrappers) {
                next(wrapper);
            }
            return this;
        }

        public WorkerWrapper<W, C> build() {
            // 本任务
            WorkerWrapper<W, C> wrapper = new WorkerWrapper<>(id, worker, param, callback);
            // 本 WorkerWrapper 有依赖
            if (dependWrappers != null) {
                for (DependWrapper workerWrapper : dependWrappers) {
                    WorkerWrapper<?, ?> dependWrapper = workerWrapper.getDependWrapper();
                    // 自己依赖的 WorkerWrapper 添加一个 nextWorkerWrapper 为本 WorkerWrapper
                    dependWrapper.addNext(wrapper);
                    wrapper.addDepend(workerWrapper);
                }
            }
            // 有依赖于本 WorkerWrapper 的 WorkerWrapper
            if (nextWrappers != null) {
                for (WorkerWrapper<?, ?> workerWrapper : nextWrappers) {
                    boolean must = selfIsMustSet != null && selfIsMustSet.contains(workerWrapper);
                    workerWrapper.addDepend(wrapper, must);
                    wrapper.addNext(workerWrapper);
                }
            }
            return wrapper;
        }
    }

}
