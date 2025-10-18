package com.freeing.batch.entity;

import java.time.LocalDateTime;

public class JobConfig {
    private String tableName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int intervalTime;

    private int minInterval;

    private int intervalDivisorFactor;

    private int threshold;

    private int threadSize;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(int intervalTime) {
        this.intervalTime = intervalTime;
    }

    public int getMinInterval() {
        return minInterval;
    }

    public void setMinInterval(int minInterval) {
        this.minInterval = minInterval;
    }

    public int getIntervalDivisorFactor() {
        return intervalDivisorFactor;
    }

    public void setIntervalDivisorFactor(int intervalDivisorFactor) {
        this.intervalDivisorFactor = intervalDivisorFactor;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getThreadSize() {
        return threadSize;
    }

    public void setThreadSize(int threadSize) {
        this.threadSize = threadSize;
    }
}
