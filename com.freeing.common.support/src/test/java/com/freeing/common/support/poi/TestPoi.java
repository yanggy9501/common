package com.freeing.common.support.poi;

import com.freeing.common.support.poi.builder.ConfigDefXMLParserBuilder;
import com.freeing.common.support.poi.excle.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author yanggy
 */
public class TestPoi {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("D:\\Project\\com.freeing.common\\com.freeing.common.support\\src\\test\\resources\\table.xml");
        ConfigDefXMLParserBuilder configDefXMLParserBuilder = new ConfigDefXMLParserBuilder(fileInputStream);
        Configuration configuration = configDefXMLParserBuilder.parse();
        fileInputStream.close();
    }
}
