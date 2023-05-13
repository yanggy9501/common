package com.freeing.common.support.pipeline.v2;

/**
 * @author yanggy
 */
public class Stage1 implements Stage<Integer , Boolean>{
    @Override
    public Boolean process(Integer input) {
        return input == null || input.compareTo(0) != 0;
    }
}
