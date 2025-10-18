package com.freeing.batch.entity;

import java.time.LocalDateTime;

public class RunningParams {
    private String tableName;

    private int threshold;

    private int minInterval;

    private int intervalDivisorFactor;

    public RunningParams(String tableName, int minInterval, int threshold, int intervalDivisorFactor) {
        this.tableName = tableName;
        this.minInterval = minInterval;
        this.threshold = threshold;
        this.intervalDivisorFactor = intervalDivisorFactor;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getMinInterval() {
        return minInterval;
    }

    public void setMinInterval(int minInterval) {
        this.minInterval = minInterval;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getIntervalDivisorFactor() {
        return intervalDivisorFactor;
    }

    public void setIntervalDivisorFactor(int intervalDivisorFactor) {
        this.intervalDivisorFactor = intervalDivisorFactor;
    }
}
