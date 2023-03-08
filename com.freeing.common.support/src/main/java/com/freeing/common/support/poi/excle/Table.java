package com.freeing.common.support.poi.excle;

import lombok.Data;

import java.util.List;

/**
 * @author yanggy
 */
@Data
public class Table {

    private String tableId;


    private String title;

    /**
     * 标题列
     */
    private List<Column> tableColumns;
}
