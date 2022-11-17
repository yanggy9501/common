package com.freeing.common.support.xml.builder;

import com.freeing.common.support.xml.exception.BuilderException;
import com.freeing.common.support.xml.paser.XPathParser;

import java.io.InputStream;

/**
 * XML 解析器
 * @param <T> 解析 xml 对应的实体类型
 *
 * @author yanggy
 */
public abstract class AbstractXMLParseBuilder<T> {
    /**
     * xml 对应的实体
     */
    private T data;

    /**
     * 是否已经解析标识
     */
    private boolean parsed;

    /**
     * xml 的 XPath 解析器
     */
    private final XPathParser parser;

    /**
     * 构造器
     *
     * @param inputStream xml 文件的输入流
     */
    public AbstractXMLParseBuilder(InputStream inputStream) {
        this.parsed = false;
        this.parser = new XPathParser(inputStream, false);
    }

    /**
     * 构造器
     *
     * @param inputStream xml 文件的输入流
     * @param validation XPathParser 解析器是否根据 dtd 校验 xml
     */
    public AbstractXMLParseBuilder(InputStream inputStream, boolean validation) {
        this.parsed = false;
        this.parser = new XPathParser(inputStream, validation);
    }

    /**
     * 解析 xml
     *
     * @return xml 对应对象
     */
    public T parse() {
        if (parsed) {
            throw new BuilderException("Each XMLParseBuilder can only be used once.");
        }
        parsed = true;
        return doParse();
    }

    /**
     * 使用 XPathParser 解析器解析 xml -> xml 对应实体
     *
     * @return xml 对应对象
     */
    protected abstract T doParse();
}
