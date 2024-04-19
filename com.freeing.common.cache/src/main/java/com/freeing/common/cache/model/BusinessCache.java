package com.freeing.common.cache.model;

import com.freeing.common.cache.model.base.CommonCache;

/**
 * 业务缓存
 */
public class BusinessCache <T> extends CommonCache {
    private T data;

    public BusinessCache<T> data(T data){
        this.data = data;
        this.exist = true;
        return this;
    }

    public BusinessCache<T> version(Long version){
        this.version = version;
        return this;
    }

    public BusinessCache<T> retryLater(){
        this.retryLater = true;
        return this;
    }

    public BusinessCache<T> notExist(){
        this.exist = false;
        this.version = -1L;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
