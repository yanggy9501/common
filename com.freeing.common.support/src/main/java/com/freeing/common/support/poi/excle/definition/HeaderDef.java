package com.freeing.common.support.poi.excle.definition;

/**
 * 表头
 *
 * @author yanggy
 */
public class HeaderDef {
    /**
     * 表头名
     */
    private String name;

    /**
     * 映射字段
     */
    private String mapperField;

    /**
     * 字段对应的类型
     */
    private String type;

    /**
     * 表头注释
     */
    private String comment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMapperField() {
        return mapperField;
    }

    public void setMapperField(String mapperField) {
        this.mapperField = mapperField;
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
