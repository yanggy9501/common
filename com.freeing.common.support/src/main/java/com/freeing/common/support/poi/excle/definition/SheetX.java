package com.freeing.common.support.poi.excle.definition;

import java.util.ArrayList;
import java.util.List;

/**
 * sheet 页
 */
public class SheetX {
    private String name;

    private List<TableX> tables = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<TableX> getTables() {
        return tables;
    }

    public void setTables(List<TableX> tables) {
        this.tables = tables;
    }
}
