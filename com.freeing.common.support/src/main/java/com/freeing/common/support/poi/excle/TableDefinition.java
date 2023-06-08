package com.freeing.common.support.poi.excle;

import java.util.List;

/**
 * 表定义
 *
 * @author yanggy
 */
public class TableDefinition {
    private String id;

    private String name;

    private List<TableHeadColumnDefinition> columns;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TableHeadColumnDefinition> getColumns() {
        return columns;
    }

    public void setColumns(List<TableHeadColumnDefinition> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "TableDefinition{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", columns=" + columns +
            '}';
    }
}
