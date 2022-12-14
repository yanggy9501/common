package com.freeing.common.component.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回结果
 *
 * @author yanggy
 */
public class PageResult implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 当前页数
     */
    private long currentPage;

    /**
     * 每页记录数
     */
    private long pageSize;

    /**
     * 总页数
     */
    private long pageCount;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<?> rows;

    public PageResult() {

    }

    public PageResult(long currentPage, long pageSize, long pageCount, long total, List<?> rows) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.pageCount = pageCount;
        this.total = total;
        this.rows = rows;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long totalCount) {
        this.total = totalCount;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
