package com.freeing.common.component.object.db;

import com.freeing.common.component.constant.Direction;

import java.io.Serializable;

/**
 * @author yanggy
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 排序的字段
     */
    private String field;

    /**
     * 排序方式（正序还是反序），默认升序
     */
    private Direction direction;

    public Order() {

    }

    public Order(String field) {
        this.field = field;
        this.direction = Direction.ASC;
    }

    public Order(String field, Direction direction) {
        this(field);
        this.direction = direction;
    }

    /**
     * @return 排序字段
     */
    public String getField() {
        return this.field;
    }

    /**
     * 设置排序字段
     * @param field 排序字段
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return 排序方向
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * 设置排序方向
     * @param direction 排序方向
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "Order{" +
            "field='" + field + '\'' +
            ", direction=" + direction +
            '}';
    }
}
