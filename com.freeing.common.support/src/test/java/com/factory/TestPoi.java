package com.factory;

import com.freeing.common.support.poi.excle.ExcelXMLParserBuilder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author yanggy
 */
public class TestPoi {
    public static void main(String[] args) throws FileNotFoundException {
        ExcelXMLParserBuilder parserBuilder = new ExcelXMLParserBuilder(new FileInputStream("D:\\Project\\com.freeing.common\\com.freeing.common.support\\src\\main\\resources\\workbook_definition_template.xml"));
        parserBuilder.parse();
    }
}
