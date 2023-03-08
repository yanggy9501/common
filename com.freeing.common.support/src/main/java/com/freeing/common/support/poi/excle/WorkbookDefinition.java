package com.freeing.common.support.poi.excle;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author yanggy
 */
@Data
public class WorkbookDefinition {

    private String templatePath;

    private String baseFileName;

    private List<Sheet> sheetList;

    /**
     * key: tableId
     */
    private Map<String, Table> tableDefinitionCache;
}
