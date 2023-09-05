package com.freeing.common.support.poi.excle.definition;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yanggy
 */
@Data
public class WorkbookDef {
    /**
     * 唯一标识
     */
    private String id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * sheets
     */
    private List<SheetDef> sheetDefs = new ArrayList<>();

    private Map<String, SheetDef> sheetDefMap = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<SheetDef> getSheetDefs() {
        return sheetDefs;
    }
}
