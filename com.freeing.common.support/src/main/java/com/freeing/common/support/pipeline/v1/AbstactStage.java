package com.freeing.common.support.pipeline.v1;

/**
 * 抽象的 Stage
 *
 * @param <T>
 */
public abstract class AbstactStage<T, R> implements Stage<T, R> {

    /**
     * 下一个 stage
     */
    private Stage<?, ?> next;

    @Override
    public Stage<?, ?> next() {
        return next;
    }

    public Stage<?, ?> getNext() {
        return next;
    }

    public void setNext(Stage<?, ?> next) {
        this.next = next;
    }
}
