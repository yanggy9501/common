package com.freeing.common.support.poi.excle.definition;

import com.freeing.common.support.reflection.Reflector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanggy
 */
public class TableDef {
    /**
     * table ID
     */
    private String id;

    /**
     * 映射 bean
     */
    private String clazz;

    /**
     * clazz 的反射器
     */
    private Reflector reflector;

    /**
     * 标题
     */
    private String title;

    /**
     * 表头列
     */
    private List<HeaderDef> headerDefs = new ArrayList<>();

    /**
     * 数据列
     */
    private List<ColumnDef> columnDefs = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public Reflector getReflector() {
        return reflector;
    }

    public void setReflector(Reflector reflector) {
        this.reflector = reflector;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HeaderDef> getHeaderDefs() {
        return headerDefs;
    }

    public void setHeaderDefs(List<HeaderDef> headerDefs) {
        this.headerDefs = headerDefs;
    }

    public List<ColumnDef> getColumnDefs() {
        return columnDefs;
    }

    public void setColumnDefs(List<ColumnDef> columnDefs) {
        this.columnDefs = columnDefs;
    }
}
