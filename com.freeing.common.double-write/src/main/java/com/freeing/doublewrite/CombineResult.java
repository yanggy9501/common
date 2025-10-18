package com.freeing.doublewrite;

public class CombineResult {
    private Object secondaryResult;

    private long secondaryCost;

    public CombineResult(Object secondaryResult, long secondaryCost) {
        this.secondaryResult = secondaryResult;
        this.secondaryCost = secondaryCost;
    }

    public Object getSecondaryResult() {
        return secondaryResult;
    }

    public void setSecondaryResult(Object secondaryResult) {
        this.secondaryResult = secondaryResult;
    }

    public long getSecondaryCost() {
        return secondaryCost;
    }

    public void setSecondaryCost(long secondaryCost) {
        this.secondaryCost = secondaryCost;
    }
}
