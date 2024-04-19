package com.freeing.common.cache.model.base;

import java.io.Serializable;

/**
 * 通用缓存模型
 * PS: 缓存没命中 --> 更新缓存，其他请求 retryLater=ture 稍后重试 --> 数据库中不存在 exist=true
 */
public class CommonCache implements Serializable {
    private static final long serialVersionUID = 0L;

    /**
     * 缓存数据是否存在，处理缓存穿透
     */
    protected boolean exist;

    /**
     * 缓存版本号，判断缓存是否过期
     */
    protected Long version;

    /**
     * 稍后再试
     */
    protected boolean retryLater;

    public boolean isExist() {
        return exist;
    }

    public void setExist(boolean exist) {
        this.exist = exist;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isRetryLater() {
        return retryLater;
    }

    public void setRetryLater(boolean retryLater) {
        this.retryLater = retryLater;
    }
}
