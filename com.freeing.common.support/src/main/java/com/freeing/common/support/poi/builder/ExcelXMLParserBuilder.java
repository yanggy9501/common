package com.freeing.common.support.poi.builder;

import com.freeing.common.support.poi.exception.DefinitionException;
import com.freeing.common.support.poi.excle.*;
import com.freeing.common.support.xml.builder.AbstractXMLParseBuilder;
import com.freeing.common.support.xml.paser.XNode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanggy
 */
public class ExcelXMLParserBuilder extends AbstractXMLParseBuilder<Configuration> {

    public ExcelXMLParserBuilder(InputStream inputStream) {
        super(inputStream);
    }

    public ExcelXMLParserBuilder(InputStream inputStream, boolean validation) {
        super(inputStream, validation);
    }

    @Override
    protected Configuration doParse() {
        XNode root = parser.evalNode("tables");
        List<XNode> tableNodeList = root.getChildren();
        for (XNode tableNode : tableNodeList) {
            TableDefinition tableDefinition = new TableDefinition();
            tableDefinition.setId(tableNode.getStringAttribute("id"));
            tableDefinition.setName(tableNode.getStringAttribute("name"));
            List<TableHeadColumnDefinition> columns = parseTableHeadColumn(tableDefinition, tableNode.getChildren());
            tableDefinition.setColumns(columns);
        }
        return data;
    }
    
    private List<TableHeadColumnDefinition> parseTableHeadColumn(TableDefinition tableDefinition,
            List<XNode> columnNodes) {
        if (columnNodes == null || columnNodes.isEmpty()) {
            throw new DefinitionException("Error definition, you have to define column node: tableId="
                + tableDefinition.getId());
        }
        ArrayList<TableHeadColumnDefinition> columnList = new ArrayList<>();
        for (XNode columnNode : columnNodes) {
            TableHeadColumnDefinition column = new TableHeadColumnDefinition();
            column.setName(columnNode.getStringAttribute("name"));
            column.setField(columnNode.getStringAttribute("field"));
            column.setComment(columnNode.getStringAttribute("comment"));
            column.setColor(columnNode.getStringAttribute("color"));
            columnList.add(column);
        }
        return columnList;
    }
}
