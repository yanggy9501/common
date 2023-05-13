package com.freeing.common.support.pipeline.v2;

/**
 *  定义一个 StageWrapper 的上下文容器 Pipeline
 *
 * @author yanggy
 */
public class Pipeline {

    public void pipeline(StageWrapper<?, ?> stageWrapepr) {
        stageWrapepr.execute(null);
    }
}
