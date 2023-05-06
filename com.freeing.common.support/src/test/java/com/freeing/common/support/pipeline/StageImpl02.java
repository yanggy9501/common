package com.freeing.common.support.pipeline;

import com.freeing.common.support.pipeline.v1.AbstactStage;

import java.math.BigDecimal;

/**
 * @author yanggy
 */
public class StageImpl02 extends AbstactStage<String, BigDecimal> {
    @Override
    public BigDecimal process(String input) {
        return new BigDecimal(input);
    }
}
