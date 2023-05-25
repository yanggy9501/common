package com.freeing.common.support.xml.paser;

import com.freeing.common.support.xml.exception.BuilderException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

/**
 * @author yanggy
 */
public class XPathParser {
    /**
     * 解析 xml 的文档
     */
    private final Document document;

    /**
     * xpath
     */
    private final XPath xpath;

    public XPathParser(InputStream inputStream, boolean validation) {
        XPathFactory factory = XPathFactory.newInstance();
        this.xpath = factory.newXPath();
        this.document = createDocument(new InputSource(inputStream));
    }

    private Document createDocument(InputSource inputSource) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(false);
            factory.setCoalescing(false);
            factory.setExpandEntityReferences(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    // NOP
                }
            });
            return builder.parse(inputSource);
        } catch (Exception e) {
            throw new BuilderException("Error create document." + e, e);
        }
    }

    /**
     * 计算 expression 所示节点
     *
     * @param expression xpath 节点路径
     * @return XNode dom节点信息
     */
    public XNode evalNode(String expression) {
        return evalNode(document, expression);
    }

    /**
     * 计算 expression 所示节点
     *
     * @param root root node of expression
     * @param expression xpath 节点路径
     * @return XNode dom节点信息
     */
    public XNode evalNode(Object root, String expression) {
        Node node = (Node) evaluate(expression, root, XPathConstants.NODE);
        if (node == null) {
            return null;
        }
        return new XNode(this, node);
    }

    private Object evaluate(String expression, Object root, QName qName) {
        try {
            return xpath.evaluate(expression, root, qName);
        } catch (Exception e) {
            throw new BuilderException("Error evaluating XPath.  Cause: " + e, e);
        }
    }
}
