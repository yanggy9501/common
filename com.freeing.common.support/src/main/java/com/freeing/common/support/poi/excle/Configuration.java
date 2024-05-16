package com.freeing.common.support.poi.excle;

import com.freeing.common.support.poi.exception.AlreadyExistException;
import com.freeing.common.support.poi.exception.NotExistException;
import com.freeing.common.support.poi.excle.def.WorkbookX;

import java.util.HashMap;
import java.util.Map;

/**
 * Excel 配置
 */
public class Configuration {

    private final Map<String, WorkbookX> workbookCache = new HashMap<>();

    public WorkbookX getWorkbook(String key) {
        if (!workbookCache.containsKey(key)) {
            throw new NotExistException("workbook definition is not exist.");
        }
        return workbookCache.get(key);
    }

    public void addWorkbook(String key, WorkbookX workbookX) {
        if (workbookCache.containsKey(key)) {
            throw new AlreadyExistException("workbook is exist already.");
        }
        workbookCache.put(key, workbookX);
    }
}
