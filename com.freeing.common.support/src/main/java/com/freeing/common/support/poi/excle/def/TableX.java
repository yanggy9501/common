package com.freeing.common.support.poi.excle.def;

import com.freeing.common.support.poi.excle.datasoruce.IDataSource;
import com.freeing.common.support.poi.excle.def.style.FontX;
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
     * clazz 的反射器
     */
    private Reflector reflector;

    /**
     * table 标题
     */
    private String title;

    /**
     * 标题样式
     */
    private FontX titleFont;

    /**
     * 表头行
     */
    private List<HeadX> heads = new ArrayList<>();

    /**
     * 数据列
     */
    private List<ColumnX> columns = new ArrayList<>();

    /**
     * 数据源
     */
    private IDataSource dataSource;

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

    public FontX getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(FontX titleFont) {
        this.titleFont = titleFont;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HeadX> getHeads() {
        return heads;
    }

    public void setHeads(List<HeadX> heads) {
        this.heads = heads;
    }

    public List<ColumnX> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnX> columns) {
        this.columns = columns;
    }

    public IDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(IDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
