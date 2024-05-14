package com.freeing.common.support.poi.excle.datasoruce;

import java.util.List;

/**
 * 线程绑定的 data source
 */
public class ThreadLocalDataSource extends IDataSource {

    public ThreadLocalDataSource(String id) {
        super(id);
    }

    @Override
    public List<Object> getData() {
        return ThreadLocalDataSourceContext.get(id);
    }
}
