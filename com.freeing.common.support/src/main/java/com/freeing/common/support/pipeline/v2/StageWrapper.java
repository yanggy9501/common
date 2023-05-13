package com.freeing.common.support.pipeline.v2;

/**
 * 一个 Stage 的封装
 *
 * @param <T> 入参
 * @param <T> 出参
 */
public class StageWrapper<T, V> {

    /**
     * 要处理的参数
     */
    private T param;

    private Stage<T, V> stage;

    private StageWrapper<?, ?> nextWrapper;

    /**
     * 也是个钩子变量，用来存临时的结果
     */
    private final StageResult<V> stageResult = StageResult.defaultResult();

    public StageWrapper(T param, Stage<T, V> stage, StageWrapper<?, ?> nextWrapper) {
        this.param = param;
        this.stage = stage;
        this.nextWrapper = nextWrapper;
    }

    public StageWrapper(Stage<T, V> stage, StageWrapper<?, ?> nextWrapper) {
        this.stage = stage;
        this.nextWrapper = nextWrapper;
    }

    public StageWrapper() {
    }

    public T getParam() {
        return param;
    }

    public void setParam(T param) {
        this.param = param;
    }

    public Stage<T, V> getStage() {
        return stage;
    }

    public void setStage(Stage<T, V> stage) {
        this.stage = stage;
    }

    public StageWrapper<?, ?> getNextWrapper() {
        return nextWrapper;
    }

    public void setNextWrapper(StageWrapper<?, ?> nextWrapper) {
        this.nextWrapper = nextWrapper;
    }

    public StageResult<V> getStageResult() {
        return stageResult;
    }

    public void execute(StageWrapper<?, ?> fromStage) {
        // fromStage == null: 开始 Stage
        if (fromStage != null && hasSuccessReuslt(fromStage)) {
            fastFail(fromStage.getStageResult().getEx());
        } else {
            try {
                V result = this.stage.process(param);
                this.stageResult.setResult(result);
                this.stageResult.setResultState(ResultState.SUCCESS);
            } catch (Exception e) {
                fastFail(e);
            }
        }
        beginNext();
    }

    private boolean hasSuccessReuslt(StageWrapper<?, ?> stageWrapper) {
        return stageWrapper.getStageResult().getResultState() != ResultState.SUCCESS;
    }

    private void beginNext() {
        if (nextWrapper == null) {
            return;
        }
        nextWrapper.execute(this);
    }

    private void fastFail(Exception ex) {
        this.stageResult.setResult(null);
        this.stageResult.setResultState(ResultState.PREV_EXCEPTION);
        this.stageResult.setEx(ex);
    }

    public void addNext(StageWrapper<?, ?> nextWrapper) {
        this.nextWrapper = nextWrapper;
    }

    public static class Builder<W, C> {

        private W param;

        private Stage<W, C> stage;

        private StageWrapper<?, ?> nextWrapper;

        public Builder<W, C> stage(Stage<W, C> stage) {
            this.stage = stage;
            return this;
        }

        public Builder<W, C> param(W w) {
            this.param = w;
            return this;
        }

        public Builder<W, C> nextWrapper(StageWrapper<?, ?> nextWrapper) {
            this.nextWrapper = nextWrapper;
            return this;
        }

        public StageWrapper<W, C> build() {
            StageWrapper<W, C> wcStageWrapper = new StageWrapper<>(stage, nextWrapper);
            if (param != null) {
                wcStageWrapper.setParam(param);
            }
            return wcStageWrapper;
        }
    }
}
