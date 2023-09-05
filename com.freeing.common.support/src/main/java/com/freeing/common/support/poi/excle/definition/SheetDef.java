package com.freeing.common.support.poi.excle.definition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanggy
 */
public class SheetDef {
    private String sheetName;

    private List<TableDef> tableDefs = new ArrayList<>();

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public List<TableDef> getTables() {
        return tableDefs;
    }

}
