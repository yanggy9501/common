package com.freeing.common.component.utils.id;

/**
 * ID 生成器
 *
 * @author yanggy
 */
public class IdGenerator {
    private final SnowFlake snowFlake;

    protected IdGenerator(long datacenterId, long machineId) {
        snowFlake = new SnowFlake(datacenterId, machineId);
    }

    public long nextId() {
        return snowFlake.nextId();
    }
}
