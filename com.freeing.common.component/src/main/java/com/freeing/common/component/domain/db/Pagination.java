package com.freeing.common.component.domain.db;

import java.io.Serializable;

/**
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
     * 每页结果数
     */
    private int pageSize;

    /**
     * 排序
     */
    private Order[] orders;

    /**
     * 构造，默认第0页，每页 {@value #DEFAULT_PAGE_SIZE} 条
     */
    public Pagination() {
        this(0, DEFAULT_PAGE_SIZE);
    }

    /**
     * 构造
     *
     * @param pageNumber 页码，0表示第一页
     * @param pageSize   每页结果数
     */
    public Pagination(int pageNumber, int pageSize) {
        this.pageNumber = Math.max(pageNumber, 0);
        this.pageSize = pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * 构造
     *
     * @param pageNumber 页码，0表示第一页
     * @param pageSize   每页结果数
     * @param order      排序对象
     */
    public Pagination(int pageNumber, int pageSize, Order order) {
        this(pageNumber, pageSize);
        this.orders = new Order[]{order};
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
