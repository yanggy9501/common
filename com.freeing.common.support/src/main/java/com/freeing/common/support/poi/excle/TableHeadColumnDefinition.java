package com.freeing.common.support.poi.excle;

/**
 * 表头定义
 *
 * @author yanggy
 */
public class TableHeadColumnDefinition {

    private String name;

    private String field;

    private String comment;

    private String color;

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "TableHeadColumnDefinition{" +
            "name='" + name + '\'' +
            ", field='" + field + '\'' +
            ", comment='" + comment + '\'' +
            ", color='" + color + '\'' +
            '}';
    }
}
