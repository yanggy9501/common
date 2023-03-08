package com.freeing.common.support.poi;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;

@Getter
@Setter
public class ExcelExportUtils {
    private int rowIndex;
    private int styleIndex;
    private String templatePath;
    private Class<?> clazz;
    private Field[] fields;
//    private CellStyle[] styles;

    public ExcelExportUtils(Class<?> clazz, int rowIndex, int styleIndex) {
        this.clazz = clazz;
        this.rowIndex = rowIndex;
        this.styleIndex = styleIndex;
        fields = clazz.getDeclaredFields();
    }

    public <T> void export(OutputStream out, InputStream templateIn, List<T> dataList) throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(templateIn);
        Sheet sheet = workbook.getSheetAt(0);

        CellStyle[] styles = getStyles(sheet.getRow(styleIndex));

//        AtomicInteger datasAi = new AtomicInteger(rowIndex);
        int datasAi = rowIndex;
        for (T t : dataList) {
            Row row = sheet.createRow(datasAi++);
            // 填充数据
            for(int i = 0; i < styles.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(styles[i]);
                Field field = fields[i];
                field.setAccessible(true);
                if (field.get(t) != null){
                    cell.setCellValue(field.get(t).toString());
                }
            }
        }
        workbook.write(out);
    }

    public CellStyle[] getStyles(Row row) {
        CellStyle [] styles = new CellStyle[row.getLastCellNum()];
        for(int i = 0; i < row.getLastCellNum(); i++) {
            styles[i] = row.getCell(i).getCellStyle();
        }
        return styles;
    }
}