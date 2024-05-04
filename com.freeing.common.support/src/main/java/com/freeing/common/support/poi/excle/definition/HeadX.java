package com.freeing.common.support.poi.excle.definition;

import com.freeing.common.support.poi.excle.convertor.Convertor;

/**
 * 表头
 */
public class HeadX {
    /**
     * 表头名
     */
    private String name;

    /**
     * 映射字段
     */
    private String field;

    private Convertor<Object, Object> convertor;

    /**
     * 字段对应的类型
     */
    private String type;

    /**
     * 批注
     */
    private String comment;

    public HeadX() {

    }

    public HeadX(String name, String field) {
        this.name = name;
        this.field = field;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Convertor<Object, Object> getConvertor() {
        return convertor;
    }

    public void setConvertor(Convertor<Object, Object> convertor) {
        this.convertor = convertor;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
