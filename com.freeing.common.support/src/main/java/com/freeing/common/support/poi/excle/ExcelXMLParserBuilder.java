package com.freeing.common.support.poi.excle;

import com.freeing.common.support.xml.builder.AbstractXMLParseBuilder;
import com.freeing.common.support.xml.paser.XNode;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanggy
 */
public class ExcelXMLParserBuilder extends AbstractXMLParseBuilder<WorkbookDefinition> {

    public ExcelXMLParserBuilder(InputStream inputStream) {
        super(inputStream);
    }

    public ExcelXMLParserBuilder(InputStream inputStream, boolean validation) {
        super(inputStream, validation);
    }

    @Override
    protected WorkbookDefinition doParse() {
        XNode root = parser.evalNode("tables");
        List<XNode> tableNodeList = root.getChildren();
//        data = new WorkbookDefinition();
//        data.setTables(new ArrayList<>());
//        for (XNode tableNode : tableNodeList) {
//            Table tableDefinition = new Table();
//            tableDefinition.setTableId(tableNode.getStringAttribute("id"));
//            tableDefinition.setName(tableNode.getStringAttribute("name"));
//
//            parseColumn(tableDefinition, tableNode.getChildren());
//
//            data.getTables().add(tableDefinition);
//        }
        System.out.println(data);
        return data;
    }
    
    private void parseColumn(Table tableDefinition, List<XNode> columnNodes) {
        if (columnNodes != null) {
            ArrayList<Column> columnList = new ArrayList<>();
            for (XNode columnNode : columnNodes) {
                Column column = new Column();
                column.setName(columnNode.getStringAttribute("name"));
                column.setField(columnNode.getStringAttribute("field"));
                column.setJavaType(columnNode.getStringAttribute("javaType", "string"));
                column.setComment(columnNode.getStringAttribute("comment"));
                column.setColor(columnNode.getStringAttribute("color"));
                columnList.add(column);
            }
            tableDefinition.setTableColumns(columnList);
        }
    }
}
