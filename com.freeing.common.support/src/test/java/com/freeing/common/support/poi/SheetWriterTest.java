package com.freeing.common.support.poi;

import com.freeing.common.support.poi.excle.Configuration;
import com.freeing.common.support.poi.excle.WorkbookWriter;
import com.freeing.common.support.poi.excle.convertor.Convertor;
import com.freeing.common.support.poi.excle.convertor.Date2StringConvertor;
import com.freeing.common.support.poi.excle.convertor.MappingConvertor;
import com.freeing.common.support.poi.excle.datasoruce.ThreadLocalDataSource;
import com.freeing.common.support.poi.excle.datasoruce.ThreadLocalDataSourceContext;
import com.freeing.common.support.poi.excle.def.*;
import com.freeing.common.support.poi.excle.def.style.FontX;
import com.freeing.common.support.reflection.Reflector;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class SheetWriterTest {

    public static void main(String[] args) throws IOException {
//        SheetWriter sheetWriter = new SheetWriter();
//        ThreadLocalDataSourceContext.add("level1",
//            Arrays.asList(
//                new Student("张三", "m", new Date()),
//                new Student("麻花", "f", new Date())
//            ));
//
//        ThreadLocalDataSourceContext.add("level2",
//            Arrays.asList(
//                new Student("李四", "m", new Date()),
//                new Student("翠花", "f", new Date())
//            ));
//
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        sheetWriter.writerSheet(sheetX(), workbook);
//
//        OutputStream out = new FileOutputStream("/Users/yangguangyin/MyDisk/volume/tmp/export.xlsx");
//        workbook.write(out);

        testWriterWb();
    }

    public static void testWriterWb() throws IOException {
        Configuration configuration = new Configuration();

        WorkbookX workbookX = new WorkbookX();
        workbookX.setId("wb1");
        workbookX.setFileName("成绩表");
        workbookX.setSheets(Arrays.asList(
            sheetX(),
            sheetX1()
        ));

        ThreadLocalDataSourceContext.add("level1",
            Arrays.asList(
                new Student("张三", "m", new Date()),
                new Student("麻花", "f", new Date())
            ));

        ThreadLocalDataSourceContext.add("level2",
            Arrays.asList(
                new Student("李四", "m", new Date()),
                new Student("翠花", "f", new Date())
            ));

        configuration.addWorkbook(workbookX.getId(), workbookX);
        WorkbookWriter workbookWriter = new WorkbookWriter(workbookX.getId(), workbookX.getFileName(), configuration);

        OutputStream out = new FileOutputStream("D:\\tmp\\export.xlsx");
        workbookWriter.write(out);
        ThreadLocalDataSourceContext.clear();
    }

    public static SheetX sheetX() {
        SheetX sheetX = new SheetX();
        sheetX.setName("学生表1");

        TableX t1 = new TableX();
        t1.setId("level1");
        t1.setClazz(Student.class.getCanonicalName());
        t1.setTitle("一年级");
        t1.setReflector(new Reflector(Student.class));
        HeadX h3 = new HeadX("生日", "birthday");
        Convertor convertor = new Date2StringConvertor("yyyy-MM-dd");
        h3.setConvertor(convertor);

        HashMap<Object, Object> map = new HashMap<>();
        map.put("f", "女");
        map.put("m", "男");
        MappingConvertor mappingConvertor = new MappingConvertor(map);
        HeadX h2 = new HeadX("性别", "sex");
        h2.setConvertor(mappingConvertor);
        t1.setHeads(Arrays.asList(
            new HeadX("姓名", "name"),
            h2,
            h3
        ));

        t1.setColumns(Arrays.asList(
            new ColumnX("name"),
            new ColumnX("sex"),
            new ColumnX("birthday")
        ));
        t1.setDataSource(new ThreadLocalDataSource("level1"));

        FontX fontX = new FontX();
        fontX.setFontHeight((short) 24);
        fontX.setColor("8");
        t1.setTitleFont(fontX);

        // ---------------------------------------------------------------------------------
        TableX t2 = new TableX();
        t2.setId("level2");
        t2.setClazz(Student.class.getCanonicalName());
        t2.setTitle("二年级");
        t2.setReflector(new Reflector(Student.class));
        t2.setHeads(Arrays.asList(
            new HeadX("姓名", "name"),
            new HeadX("性别", "sex"),
            new HeadX("生日", "birthday")
        ));

        t2.setColumns(Arrays.asList(
            new ColumnX("name"),
            new ColumnX("sex"),
            new ColumnX("birthday")
        ));
        t2.setDataSource(new ThreadLocalDataSource("level2"));

        sheetX.setTables(Arrays.asList(
            t1,
            t2
        ));

        return sheetX;
    }

    public static SheetX sheetX1() {
        SheetX sheetX = new SheetX();
        sheetX.setName("学生表2");

        TableX t1 = new TableX();
        t1.setId("level1");
        t1.setClazz(Student.class.getCanonicalName());
        t1.setTitle("一年级");
        t1.setReflector(new Reflector(Student.class));
        t1.setHeads(Arrays.asList(
            new HeadX("姓名", "name"),
            new HeadX("性别", "sex"),
            new HeadX("生日", "birthday")
        ));

        t1.setColumns(Arrays.asList(
            new ColumnX("name"),
            new ColumnX("sex"),
            new ColumnX("birthday")
        ));
        t1.setDataSource(new ThreadLocalDataSource("level1"));

        TableX t2 = new TableX();
        t2.setId("level2");
        t2.setClazz(Student.class.getCanonicalName());
        t2.setTitle("二年级");
        t2.setReflector(new Reflector(Student.class));
        t2.setHeads(Arrays.asList(
            new HeadX("姓名", "name"),
            new HeadX("性别", "sex"),
            new HeadX("生日", "birthday")
        ));

        t2.setColumns(Arrays.asList(
            new ColumnX("name"),
            new ColumnX("sex"),
            new ColumnX("birthday")
        ));
        t2.setDataSource(new ThreadLocalDataSource("level2"));

        sheetX.setTables(Arrays.asList(
            t1,
            t2
        ));

        return sheetX;
    }
}
