package com.freeing.common.support.poi.excle.datasoruce;

import java.util.List;

/**
 * 数据源
 */
public abstract class IDataSource {
    protected String id;

    public IDataSource(String id) {
        this.id = id;
    }

    public abstract List<Object> getData();
}
