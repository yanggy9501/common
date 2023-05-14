package com.freeing.common.async.worker;

import com.freeing.common.async.wrapper.WorkerWrapper;

/**
 * 对依赖的wrapper的封装
 */
public class DependWrapper {
    /**
     * 依赖的 WorkerWrapper
     */
    private WorkerWrapper<?, ?> dependWrapper;

    /**
     * 是否该依赖必须完成后才能执行自己.
     * 因为存在一个任务，依赖于多个任务，是让这多个任务全部完成后才执行自己，还是某几个执行完毕就可以执行自己
     */
    private boolean must = true;

    public DependWrapper(WorkerWrapper<?, ?> dependWrapper, boolean must) {
        this.dependWrapper = dependWrapper;
        this.must = must;
    }

    public DependWrapper() {
    }

    public WorkerWrapper<?, ?> getDependWrapper() {
        return dependWrapper;
    }

    public void setDependWrapper(WorkerWrapper<?, ?> dependWrapper) {
        this.dependWrapper = dependWrapper;
    }

    public boolean isMust() {
        return must;
    }

    public void setMust(boolean must) {
        this.must = must;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DependWrapper)) {
            return false;
        }
        DependWrapper that = (DependWrapper) o;
        if (isMust() != that.isMust()) {
            return false;
        }
        return getDependWrapper().equals(that.getDependWrapper());
    }

    @Override
    public int hashCode() {
        int result = getDependWrapper().hashCode();
        result = 31 * result + (isMust() ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DependWrapper{" +
                "dependWrapper=" + dependWrapper +
                ", must=" + must +
                '}';
    }
}
