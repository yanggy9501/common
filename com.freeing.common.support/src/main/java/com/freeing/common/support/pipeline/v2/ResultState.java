package com.freeing.common.support.pipeline.v2;

/**
 * 结果状态
 */
public enum ResultState {
    SUCCESS,
    EXCEPTION,
    PREV_EXCEPTION, // 上游异常
    DEFAULT  //默认状态
}
