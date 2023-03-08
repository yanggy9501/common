package com.freeing.common.support.poi;

import com.freeing.common.support.poi.excle.Table;
import lombok.Getter;
import lombok.Setter;

import java.io.OutputStream;
import java.util.List;

@Getter
@Setter
public class ExcelExportUtils2 {


    public <T> void export(OutputStream out, Table tableDefinition, List<T> dataList) throws Exception {

    }
}