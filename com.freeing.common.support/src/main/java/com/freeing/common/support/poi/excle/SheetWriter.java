package com.freeing.common.support.poi.excle;

import com.freeing.common.support.poi.exception.ExportException;
import com.freeing.common.support.poi.excle.def.HeadX;
import com.freeing.common.support.poi.excle.def.SheetX;
import com.freeing.common.support.poi.excle.def.TableX;
import com.freeing.common.support.reflection.Reflector;
import com.freeing.common.support.reflection.invoker.Invoker;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SheetWriter {
    private int nextRow = 0;
    private XSSFSheet currentSheet;

    public void writerSheet(SheetX sheetX, XSSFWorkbook workbook) {
        // 获取 sheet 定义
        String sheetName = sheetX.getName();
        // 创建 sheet
        currentSheet = workbook.createSheet(sheetName);

        // 编列 sheet 中的 table
        List<TableX> tables = sheetX.getTables();
        for (int i = 0, size = tables.size(); i < size; i++) {
            TableX table = tables.get(i);
            writerTableTitle(table);
            writerTableHead(table);
            if (table.getDataSource() == null) {
                continue;
            }
            List<Object> data = table.getDataSource().getData();
            if (data != null && !data.isEmpty()) {
                try {
                    writerTableData(table, data);
                } catch (Exception e) {
                   throw new ExportException(e);
                }
            }

            nextRow();
        }
    }

    protected void writerTableTitle(TableX table) {
        String title = table.getTitle();
        if (title != null && !title.isEmpty()) {
            int titleRowIdx = nextRow();
            XSSFRow titleRow = currentSheet.createRow(titleRowIdx);
            // 填充 title
            XSSFCell titleCell = titleRow.createCell(0, CellType.STRING);
            titleCell.setCellValue(title);

            int end = 0;
            if (table.getHeads() != null && !table.getHeads().isEmpty()) {
                end = table.getHeads().size() - 1;
            }
            // 合并单元格
            currentSheet.addMergedRegion(new CellRangeAddress(titleRowIdx, titleRowIdx,0, end));

            // title 样式
        }
    }

    protected void writerTableHead(TableX table) {
        // table 表头
        List<HeadX> heads = table.getHeads();
        XSSFRow headRow = currentSheet.createRow(nextRow());
        XSSFCell headCell;
        HeadX head;
        for (int i = 0, size = heads.size(); i < size; i++) {
            head = heads.get(i);
            headCell = headRow.createCell(i, CellType.STRING);
            // 设置单元格样式，列宽

            headCell.setCellValue(head.getName());
            // 批注
            if (head.getComment() != null && !head.getComment().isEmpty()) {

            }
        }
    }

    protected void writerTableData(TableX table, List<Object> data)
            throws InvocationTargetException, IllegalAccessException {
        Reflector reflector = table.getReflector();
        XSSFRow dataRow;
        for (int rowIdx = 0, size = data.size(); rowIdx < size; rowIdx++) {
            Object obj = data.get(rowIdx);
            dataRow = currentSheet.createRow(nextRow());
            if (obj == null) {
                continue;
            }
            int cellIdx = 0;
            for (HeadX head : table.getHeads()) {
                // 创建数据单元格
                XSSFCell cell = dataRow.createCell(cellIdx++);
                // 设置单元格样式

                Invoker invoker = reflector.getGetterInvoker(head.getField());
                Object value = invoker.invoke(obj);
                if (head.getConvertor() != null) {
                    value = head.getConvertor().convert(value);
                }
                Class<?> getterType = reflector.getGetterType(head.getField());
                if (value == null) {
                    // 值为空
                    continue;
                }
                else if (getterType == byte.class ||
                    getterType == short.class ||
                    getterType == int.class ||
                    getterType == long.class) {
                    cell.setCellValue((double) value);
                }
                else if (getterType == Byte.class ||
                    getterType == Short.class ||
                    getterType == Integer.class ||
                    getterType == Long.class) {
                    cell.setCellValue(((Number) value).doubleValue());
                }
                else if (value instanceof String) {
                    cell.setCellValue((String) value);
                }
                else if (value instanceof Date) {
                    cell.setCellValue((Date) value);
                }
                else if (value instanceof LocalDateTime) {
                    cell.setCellValue((LocalDateTime) value);
                }
                else if (value instanceof LocalDate) {
                    cell.setCellValue((LocalDate) value);
                }
                else if (value instanceof Calendar) {
                    cell.setCellValue((Calendar) value);
                }
                else if (value instanceof RichTextString) {
                    cell.setCellValue((RichTextString) value);
                }
                else if (value instanceof BigDecimal) {
                    cell.setCellValue(((BigDecimal) value).toPlainString());
                }
                else {
                    cell.setCellValue(value.toString());
                }
            }
        }
    }

    protected int nextRow() {
        return nextRow++;
    }
}
