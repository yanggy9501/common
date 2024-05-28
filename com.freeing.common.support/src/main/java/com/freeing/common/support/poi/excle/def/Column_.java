package com.freeing.common.support.poi.excle.def;

import com.freeing.common.support.poi.excle.convertor.Convertor;
import com.freeing.common.support.poi.excle.def.style.CellStyleX;
import com.freeing.common.support.poi.excle.def.style.Font_;

/**
 * 数据列
 */
public class Column_ {
    /* ------------------------------- header ------------------------------- */

    private String headerName;

    private String comment;

    private Font_ headerFont;

    private CellStyleX headerCellStyle;

    /* ------------------------------- header ------------------------------- */

    /* ------------------------------- column ------------------------------- */

    /**
     * 映射字段
     */
    private String fieldName;

    private String fieldType;

    private Font_ filedFont;

    private CellStyleX filedCellStyle;

    /**
     * 数据转换器
     */
    private Convertor<Object, Object> convertor;

    /* ------------------------------- column ------------------------------- */

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Font_ getHeaderFont() {
        return headerFont;
    }

    public void setHeaderFont(Font_ headerFont) {
        this.headerFont = headerFont;
    }

    public CellStyleX getHeaderCellStyle() {
        return headerCellStyle;
    }

    public void setHeaderCellStyle(CellStyleX headerCellStyle) {
        this.headerCellStyle = headerCellStyle;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Font_ getFiledFont() {
        return filedFont;
    }

    public void setFiledFont(Font_ filedFont) {
        this.filedFont = filedFont;
    }

    public CellStyleX getFiledCellStyle() {
        return filedCellStyle;
    }

    public void setFiledCellStyle(CellStyleX filedCellStyle) {
        this.filedCellStyle = filedCellStyle;
    }

    public Convertor<Object, Object> getConvertor() {
        return convertor;
    }

    public void setConvertor(Convertor<Object, Object> convertor) {
        this.convertor = convertor;
    }
}
