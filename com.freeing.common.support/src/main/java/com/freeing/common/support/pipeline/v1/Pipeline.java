package com.freeing.common.support.pipeline.v1;

/**
 *  定义一个Pipeline接口，表示一个流水线
 *
 * @author yanggy
 */
public interface Pipeline<T, R> {

    default R execute(T input) {
        return null;
    }
}
