package com.freeing.common.support.pipeline;

import com.freeing.common.support.pipeline.v1.AbstactStage;

import java.math.BigDecimal;

/**
 * @author yanggy
 */
public class StageImpl03 extends AbstactStage<BigDecimal, Double> {
    @Override
    public Double process(BigDecimal input) {
        return input.doubleValue();
    }
}
