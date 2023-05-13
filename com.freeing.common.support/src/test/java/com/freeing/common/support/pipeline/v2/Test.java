package com.freeing.common.support.pipeline.v2;

/**
 * @author yanggy
 */
public class Test {
    public static void main(String[] args) {
        Stage1 stage1 = new Stage1();
        Stage2 stage2 = new Stage2();
        Stage3 stage3 = new Stage3();

        StageWrapper<StageResult<String>, String> wrapper3 = new StageWrapper.Builder<StageResult<String>, String>()
            .stage(stage3)
            .build();


        StageWrapper<StageResult<Boolean>, String> wrapper2 = new StageWrapper.Builder<StageResult<Boolean>, String>()
            .nextWrapper(wrapper3)
            .stage(stage2)
            .build();

        StageWrapper<Integer, Boolean> wrapper1 = new StageWrapper.Builder<Integer, Boolean>()
            .param(10)
            .stage(stage1)
            .nextWrapper(wrapper2)
            .build();

        wrapper2.setParam(wrapper1.getStageResult());
        wrapper3.setParam(wrapper2.getStageResult());

        wrapper1.execute(null);
        System.out.println("");
    }
}
