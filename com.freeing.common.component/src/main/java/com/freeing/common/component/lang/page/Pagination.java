package com.freeing.common.component.lang.page;

import com.freeing.common.component.lang.order.Order;

import java.io.Serializable;

/**
 * 分页
 *
 * @author yanggy
 */
public class Pagination implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 页码，0表示第一页
     */
    private int pageNumber;

    /**
     * 每页大小
     */
    private int pageSize;

    /**
     * 排序
     */
    private Order[] orders;

    public Pagination() {
        this(0, DEFAULT_PAGE_SIZE);
    }

    public Pagination(int pageNumber, int pageSize) {
        this.pageNumber = Math.max(pageNumber, 0);
        this.pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public Pagination(int pageNumber, int pageSize, Order order) {
        this(pageNumber, pageSize);
        this.orders = new Order[]{order};
    }

    public Pagination(int pageNumber, int pageSize, Order[] orders) {
        this(pageNumber, pageSize);
        this.orders = orders;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = Math.max(pageNumber, 0);
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = (pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
    }

    public Order[] getOrders() {
        return this.orders;
    }

    public void setOrder(Order... orders) {
        this.orders = orders;
    }
}
