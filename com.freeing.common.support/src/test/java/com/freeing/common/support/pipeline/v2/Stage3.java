package com.freeing.common.support.pipeline.v2;

/**
 * @author yanggy
 */
public class Stage3 implements Stage<StageResult<String> , String>{
    @Override
    public String process(StageResult<String> input) {
        return input.getResult() + " enhance";
    }
}
