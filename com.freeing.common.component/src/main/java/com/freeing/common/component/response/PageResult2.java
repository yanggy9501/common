package com.freeing.common.component.response;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回结果
 *
 * @author yanggy
 */
public class PageResult2<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<T> items;

    private Pagination pagination;

    public PageResult2() {

    }

    public static class Pagination {
        /**
         * 当前页数
         */
        private long currentPage;

        /**
         * 下一页数
         */
        private long nextPage;

        /**
         * 每页记录数
         */
        private long pageSize;

        /**
         * 总页数
         */
        private long totalPage;

        /**
         * 总记录数
         */
        private long total;

        private boolean hasMore;

        public long getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(long currentPage) {
            this.currentPage = currentPage;
        }

        public long getNextPage() {
            return nextPage;
        }

        public void setNextPage(long nextPage) {
            this.nextPage = nextPage;
        }

        public long getPageSize() {
            return pageSize;
        }

        public void setPageSize(long pageSize) {
            this.pageSize = pageSize;
        }

        public long getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(long totalPage) {
            this.totalPage = totalPage;
        }

        public long getTotal() {
            return total;
        }

        public void setTotal(long total) {
            this.total = total;
        }

        public boolean isHasMore() {
            return hasMore;
        }

        public void setHasMore(boolean hasMore) {
            this.hasMore = hasMore;
        }
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
