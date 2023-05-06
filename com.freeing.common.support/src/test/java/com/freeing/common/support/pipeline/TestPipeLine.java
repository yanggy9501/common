package com.freeing.common.support.pipeline;

/**
 * @author yanggy
 */
public class TestPipeLine {
    public static void main(String[] args) {
        StageImpl01 stageImpl01 = new StageImpl01();
        StageImpl02 stageImpl02 = new StageImpl02();
        StageImpl03 stageImpl03 = new StageImpl03();
        stageImpl01.setNext(stageImpl02);
        stageImpl02.setNext(stageImpl03);

        stageImpl01.process(new Integer(100));
    }
}
