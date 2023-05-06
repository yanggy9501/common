package com.freeing.common.support.pipeline;

import com.freeing.common.support.pipeline.v1.AbstactStage;

/**
 * @author yanggy
 */
public class StageImpl01 extends AbstactStage<Integer, String> {
    @Override
    public String process(Integer input) {
        return input.toString();
    }
}
