package com.freeing.common.support.poi.excle;

import java.util.List;

/**
 * 数据源
 */
public abstract class AbstractDataSource {
    private String tableId;

    public AbstractDataSource(String tableId) {
        this.tableId = tableId;
    }

    public abstract List<Object> getDataSource();
}
