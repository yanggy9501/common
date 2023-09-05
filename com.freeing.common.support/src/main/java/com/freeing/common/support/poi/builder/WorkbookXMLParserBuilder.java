package com.freeing.common.support.poi.builder;

import com.freeing.common.support.poi.exception.DefinitionException;
import com.freeing.common.support.poi.excle.Configuration;
import com.freeing.common.support.poi.excle.definition.ColumnDef;
import com.freeing.common.support.poi.excle.definition.SheetDef;
import com.freeing.common.support.poi.excle.definition.TableDef;
import com.freeing.common.support.poi.excle.definition.WorkbookDef;
import com.freeing.common.support.xml.builder.AbstractXMLParseBuilder;
import com.freeing.common.support.xml.paser.XNode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yanggy
 */
public class WorkbookXMLParserBuilder extends AbstractXMLParseBuilder<WorkbookDef> {

    Configuration configuration;

    public WorkbookXMLParserBuilder(InputStream inputStream, Configuration configuration) {
        super(inputStream);
        this.configuration = configuration;
    }

    public WorkbookXMLParserBuilder(InputStream inputStream, boolean validation, Configuration configuration) {
        super(inputStream, validation);
        this.configuration = configuration;
    }

    @Override
    protected WorkbookDef doParse() {
        this.data = new WorkbookDef();
        XNode workbook = parser.evalNode("workbook");
        String id = workbook.getStringAttribute("id");
        Objects.requireNonNull(id, "workbook id can be null");
        data.setId(id);

        String fileName = workbook.getStringAttribute("fileName");
        Objects.requireNonNull(fileName, "workbook fileName can be null");
        data.setFileName(fileName);
        parseSheets(workbook);
        return data;
    }

    private void parseSheets(XNode workbook) {
        List<XNode> sheetNodeList = workbook.getChildren();
        for (XNode sheetNode : sheetNodeList) {
            SheetDef sheetDef = new SheetDef();
            String sheetName = sheetNode.getStringAttribute("sheetName");
            if (sheetName != null && !sheetName.isEmpty()) {
                sheetDef.setName(sheetName);
            }

            List<XNode> tableNodeList = sheetNode.getChildren();

            for (XNode tableNode : tableNodeList) {
                String tableId = tableNode.getStringAttribute("id");
                if (!configuration.getTableDefinitionMap().containsKey(tableId)) {
                    throw new DefinitionException(tableId + "tableDef definition is not exist.");
                }
                TableDef tableDef = configuration.getTableDefinitionMap().get(tableId);

                sheetDef.getTables().add(tableDef);

                XNode titleNode = tableNode.evalNode("title");
                if (titleNode  != null) {
                    String title = titleNode.getBody();
                    if (title != null) {
                        tableDef.setTitle(title.trim());
                    }
                }

                XNode tableBodyNode = tableNode.evalNode("tableDef-body");
                List<XNode> columnNodeList = tableBodyNode.getChildren();
                List<ColumnDef> columnDefs = new ArrayList<>();
                for (XNode columnNode : columnNodeList) {
                    ColumnDef columnDef = new ColumnDef();

                    String field = columnNode.getStringAttribute("field");
                    Objects.requireNonNull(field, "tableDef " + tableId + "' field can not be null.");
                    columnDef.setField(field);

                    columnDefs.add(columnDef);
                }
                tableDef.setColumns(columnDefs);
            }
            data.getSheetDefs().add(sheetDef);
        }
    }

}
