package com.freeing.common.component.util;

import com.freeing.common.component.model.PageResult;

import java.util.List;

/**
 * 分页工具类
 *
 * @author yanggy
 * @date 2021/11/6 22:57
 */
public class PageUtils {
    /**
     * 分页
     *
     * @param current 当前页数
     * @param limit 每页记录数
     * @param totalPage 列表数据
     * @param totalCount 总记录数
     * @param rows 列表数据
     * @return PageEntity
     */
    public static PageResult createPage(long current, long limit, long totalPage, long totalCount, List<?> rows) {
        return new PageResult(current, limit, totalPage, totalCount, rows);
    }
}
