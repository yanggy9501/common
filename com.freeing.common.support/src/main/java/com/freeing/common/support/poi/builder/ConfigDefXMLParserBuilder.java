package com.freeing.common.support.poi.builder;

import com.freeing.common.support.poi.Resources;
import com.freeing.common.support.poi.exception.DefinitionException;
import com.freeing.common.support.poi.excle.Configuration;
import com.freeing.common.support.poi.excle.definition.HeaderDef;
import com.freeing.common.support.poi.excle.definition.TableDef;
import com.freeing.common.support.poi.excle.definition.WorkbookDef;
import com.freeing.common.support.reflection.Reflector;
import com.freeing.common.support.xml.builder.AbstractXMLParseBuilder;
import com.freeing.common.support.xml.paser.XNode;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author yanggy
 */
public class ConfigDefXMLParserBuilder extends AbstractXMLParseBuilder<Configuration> {

    public ConfigDefXMLParserBuilder(InputStream inputStream) {
        super(inputStream);
    }

    public ConfigDefXMLParserBuilder(InputStream inputStream, boolean validation) {
        super(inputStream, validation);
    }

    @Override
    protected Configuration doParse() {
        this.data = new Configuration();

        // 解析 table definition
        XNode configurationNode = parser.evalNode("configuration");

        XNode tablesNode = configurationNode.evalNode("tables");
        List<XNode> tableNodeList = tablesNode.getChildren();
        if (tableNodeList == null) {
            throw new DefinitionException("tables node cant not be empty.");
        }
        for (XNode tableNode : tableNodeList) {
            TableDef tableDef = new TableDef();
            // 非空判断
            String id = tableNode.getStringAttribute("id");
            Objects.requireNonNull(id, "id can not be null.");
            String clazz = tableNode.getStringAttribute("class");
            Objects.requireNonNull(id, id + "' class can not be null.");
            tableDef.setId(id);
            tableDef.setClazz(clazz);
            Reflector reflector = null;
            try {
                reflector = new Reflector(Class.forName(clazz));
                tableDef.setReflector(reflector);
            } catch (ClassNotFoundException e) {
                throw new DefinitionException("class " + clazz + "is not exist.");
            }
            List<XNode> headers = tableNode.getChildren();
            if (data.getTableDefinitionMap().containsKey(id)) {
                throw new DefinitionException("key '" + id + "' is exist already.");
            }
            data.getTableDefinitionMap().put(id, tableDef);
        }
        parseWorkbooks(configurationNode.evalNode("workbooks"));
        return data;
    }

    /**
     * 解析表头
     *
     * @param tableDef
     * @param headerNodes
     */
    private void parseTableHeader(TableDef tableDef, List<XNode> headerNodes) {
        if (headerNodes == null || headerNodes.isEmpty()) {
            throw new DefinitionException("Error definition, header node can not be empty, tableDef id="
                + tableDef.getId());
        }
        ArrayList<HeaderDef> headerDefs = new ArrayList<>();
        for (XNode headerNode : headerNodes) {
            HeaderDef headerDef = new HeaderDef();

            String name = headerNode.getStringAttribute("name");
            Objects.requireNonNull(name, "headerDef' name can not be null");
            headerDef.setName(name);

            String mapperField = headerNode.getStringAttribute("mapperField");
            Objects.requireNonNull(name, "headerDef' mapperField can not be null");
            headerDef.setName(mapperField);

            String comment = headerNode.getStringAttribute("comment");
            headerDef.setName(comment);

            headerDefs.add(headerDef);
        }
        tableDef.setHeaderDefs(headerDefs);
    }

    /**
     * 解析 workbook
     *
     * @param workbooksNode
     * @throws FileNotFoundException
     */
    private void parseWorkbooks(XNode workbooksNode) {
        List<XNode> workbookNodeList = workbooksNode.getChildren();
        for (XNode workbookNode : workbookNodeList) {
            String resource = workbookNode.getStringAttribute("resource");
            InputStream inputStream = null;
            try {
                inputStream = Resources.getResourceAsStream(resource, this.getClass().getClassLoader());
                WorkbookXMLParserBuilder workbookXMLParserBuilder = new WorkbookXMLParserBuilder(inputStream, data);
                WorkbookDef workbookDef = workbookXMLParserBuilder.parse();
                if (data.getWorkbookDefinitionMap().containsKey(workbookDef.getId())) {
                    throw new DefinitionException("workbookDef " + workbookDef.getId() + "is exist already.");
                }
                data.getWorkbookDefinitionMap().put(workbookDef.getId(), workbookDef);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
