package com.freeing.common.support.xml.paser;

import org.w3c.dom.CharacterData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * xml Node 扩展节点
 *
 * @author yanggy
 */
public class XNode {
    /**
     * xml 的一个节点，XNode 对 Node 进行扩展
     */
    private Node node;

    /**
     * 标签名
     */
    private String name;

    /**
     * 标签 body
     */
    private String body;

    /**
     * 标签属性
     */
    private Properties attributes;

    /**
     * xml 解析器
     */
    private XPathParser xpathParser;

    public XNode(XPathParser xPathParser, Node node) {
        this.node = node;
        this.name = node.getNodeName();
        this.body = parseBody(node);
        this.attributes = parseAttributes(node);
        this.xpathParser = xPathParser;
    }

    private Properties parseAttributes(Node node) {
        Properties attributes = new Properties();
        NamedNodeMap attributeNodes = node.getAttributes();
        if (attributeNodes != null) {
            for (int i = 0; i < attributeNodes.getLength(); i++) {
                Node attribute = attributeNodes.item(i);
                attributes.put(attribute.getNodeName(), attribute.getNodeValue());
            }
        }
        return attributes;
    }

    /**
     * 获取标签内纯文本
     *
     * @param node Node
     * @return String
     */
    private String parseBody(Node node) {
        String data = getBodyData(node);
        if (data == null) {
            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                data = getBodyData(child);
                if (data != null) {
                    break;
                }
            }
        }
        return data;
    }

    private String getBodyData(Node child) {
        if (child.getNodeType() == Node.CDATA_SECTION_NODE
            || child.getNodeType() == Node.TEXT_NODE) {
            return ((CharacterData) child).getData();
        }
        return null;
    }

    /**
     * 获取属性的值
     *
     * @param name 属性 key
     * @return 属性值
     */
    public String getStringAttribute(String name) {
        return getStringAttribute(name, null);
    }

    /**
     * 获取属性的值
     *
     * @param name 属性 key
     * @param defValue 默认值
     * @return 属性值
     */
    public String getStringAttribute(String name, String defValue) {
        String value = attributes.getProperty(name);
        return value == null ? defValue : value;
    }

    /**
     * 获取当前节点孩子节点
     *
     * @return List<XNode>
     */
    public List<XNode> getChildren() {
        List<XNode> children = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0, n = nodeList.getLength(); i < n; i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                children.add(new XNode(xpathParser, node));
            }
        }
        return children;
    }

    /**
     * 解析标签为 Properties
     * 如：<property name="key" value="value"/>
     *
     * @return Properties
     */
    public Properties getChildrenAsProperties() {
        Properties properties = new Properties();
        for (XNode child : getChildren()) {
            String name = child.getStringAttribute("name");
            String value = child.getStringAttribute("value");
            if (name != null && value != null) {
                properties.setProperty(name, value);
            }
        }
        return properties;
    }

    /**************************************** getter **************************************/

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public Properties getAttributes() {
        return attributes;
    }
}
