package com.freeing.common.support.poi.excle.def;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanggy
 */
public class WorkbookX {
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
    private List<SheetX> sheets = new ArrayList<>();

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

    public List<SheetX> getSheets() {
        return sheets;
    }

    public void setSheets(List<SheetX> sheets) {
        this.sheets = sheets;
    }
}
