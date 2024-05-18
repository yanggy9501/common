package com.freeing.common.support.poi.excle;

import com.freeing.common.support.poi.excle.def.SheetX;
import com.freeing.common.support.poi.excle.def.WorkbookX;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class WorkbookWriter {
    private final String id;

    private final String name;

    private final Configuration configuration;

    public WorkbookWriter(String id, String name, Configuration configuration) {
        this.id = id;
        this.name = name;
        this.configuration = configuration;
    }

    public void write(OutputStream out) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        WorkbookX workbookX = configuration.getWorkbook(id);

        List<SheetX> sheetXs = workbookX.getSheets();
        for (SheetX sheetX : sheetXs) {
            SheetWriter sheetWriter = new SheetWriter();
            sheetWriter.writeSheet(sheetX, workbook);
        }
        workbook.write(out);
        workbook.close();
    }
}
