package com.freeing.common.support.poi.excle.def;

import com.freeing.common.support.poi.excle.datasoruce.IDataSource;
import com.freeing.common.support.poi.excle.def.style.CellStyleX;
import com.freeing.common.support.poi.excle.def.style.Font_;
import com.freeing.common.support.reflection.Reflector;

import java.util.ArrayList;
import java.util.List;

/**
 * 表
 */
public class TableX {
    /**
     * table ID
     */
    private String id;

    /**
     * class 全限定名称
     */
    private String clazz;

    /**
     * 反射器
     */
    private Reflector reflector;

    /**
     * 标题
     */
    private String title;

    /**
     * 标题样式
     */
    private Font_ titleFont;

    private CellStyleX titleCellStyle;

    private List<Column_> columns = new ArrayList<>();

    /**
     * 数据源
     */
    private IDataSource dataSource;

    private int sort;

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

    public Font_ getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(Font_ titleFont) {
        this.titleFont = titleFont;
    }

    public CellStyleX getTitleCellStyle() {
        return titleCellStyle;
    }

    public void setTitleCellStyle(CellStyleX titleCellStyle) {
        this.titleCellStyle = titleCellStyle;
    }

    public List<Column_> getColumns() {
        return columns;
    }

    public void setColumns(List<Column_> columns) {
        this.columns = columns;
    }

    public IDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
