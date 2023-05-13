package com.freeing.common.support.pipeline.v2;

/**
 * 定义一个 Stage 接口，表示 pipeline 的一个处理阶段
 *
 * @param <T>
 */
public interface Stage<T, R> {

    /**
     * 处理输入数据，并返回输出数据
     *
     * @param input 输入参数
     * @return
     */
    R process(T input);
}
