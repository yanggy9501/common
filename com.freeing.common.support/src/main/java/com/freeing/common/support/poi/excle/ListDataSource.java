package com.freeing.common.support.poi.excle;

import java.util.List;

/**
 * 数据源
 */
public class ListDataSource extends AbstractDataSource {
    List<Object> data;

    public ListDataSource(String tableId, List<Object> data) {
        super(tableId);
        this.data = data;
    }

    @Override
    public List<Object> getDataSource() {
        return data;
    }
}
