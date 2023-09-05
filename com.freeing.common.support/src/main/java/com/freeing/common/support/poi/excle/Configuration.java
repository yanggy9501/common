package com.freeing.common.support.poi.excle;

import com.freeing.common.support.poi.excle.definition.TableDef;
import com.freeing.common.support.poi.excle.definition.WorkbookDef;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Excel 配置
 *
 * @author yanggy
 */
public class Configuration {

    private final Set<String> workbookNamespanceSet = new HashSet<>();

    private final Map<String, WorkbookDef> WorkbookDefinitionMap = new HashMap<>();

    private final Map<String, TableDef> tableDefinitionMap = new HashMap<>();

    public Map<String, TableDef> getTableDefinitionMap() {
        return tableDefinitionMap;
    }

    public Map<String, WorkbookDef> getWorkbookDefinitionMap() {
        return WorkbookDefinitionMap;
    }
}
