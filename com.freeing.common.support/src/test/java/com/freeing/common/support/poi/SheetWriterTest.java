package com.freeing.common.support.poi;

import com.freeing.common.support.poi.excle.ListDataSource;
import com.freeing.common.support.poi.excle.SheetWriter;
import com.freeing.common.support.poi.excle.definition.ColumnX;
import com.freeing.common.support.poi.excle.definition.HeadX;
import com.freeing.common.support.poi.excle.definition.SheetX;
import com.freeing.common.support.poi.excle.definition.TableX;
import com.freeing.common.support.reflection.Reflector;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Date;

public class SheetWriterTest {
    public static void main(String[] args) throws IOException {
        SheetWriter sheetWriter = new SheetWriter();
        sheetWriter.setDataSources(Arrays.asList(
            new ListDataSource("student1", Arrays.asList(
                new Student("张三", "m", new Date()),
                new Student("麻花", "f", new Date())
                )),
            new ListDataSource("student2", Arrays.asList(
                new Student("李四", "m", new Date()),
                new Student("翠花", "f", new Date())
            ))
        ));
        XSSFWorkbook workbook = new XSSFWorkbook();
        sheetWriter.writerSheet(sheetX(), workbook);

        OutputStream out = new FileOutputStream("/Users/yangguangyin/MyDisk/volume/tmp/export.xlsx");
        workbook.write(out);
    }

    public static SheetX sheetX() {
        SheetX sheetX = new SheetX();
        sheetX.setName("学生表");

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

        sheetX.setTables(Arrays.asList(
            t1,
            t2
        ));

        return sheetX;
    }
}
