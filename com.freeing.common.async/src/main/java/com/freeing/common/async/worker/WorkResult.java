package com.freeing.common.async.worker;

/**
 * 执行结果的封装
 *
 * @author yanggy
 */
public class WorkResult<V> {
    /**
     * 执行的结果
     */
    private V result;

    /**
     * 结果状态
     */
    private ResultState resultState;

    /**
     * 异常信息
     */
    private Exception ex;

    public WorkResult(V result, ResultState resultState) {
        this(result, resultState, null);
    }

    public WorkResult(V result, ResultState resultState, Exception ex) {
        this.result = result;
        this.resultState = resultState;
        this.ex = ex;
    }

    public static <V> WorkResult<V> defaultResult() {
        return new WorkResult<>(null, ResultState.DEFAULT);
    }

    public V getResult() {
        return result;
    }

    public void setResult(V result) {
        this.result = result;
    }

    public ResultState getResultState() {
        return resultState;
    }

    public void setResultState(ResultState resultState) {
        this.resultState = resultState;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }


    @Override
    public String toString() {
        return "WorkResult{" +
            "result=" + result +
            ", resultState=" + resultState +
            ", ex=" + ex +
            '}';
    }
}
