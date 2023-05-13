package com.freeing.common.support.pipeline.v2;

/**
 * @author yanggy
 */
public class Stage2 implements Stage<StageResult<Boolean> , String>{
    @Override
    public String process(StageResult<Boolean> input) {
        return input.getResult().toString();
    }
}
