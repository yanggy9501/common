package com.freeing.common.support.poi.excle.builder;

import com.freeing.common.support.poi.exception.DefinitionException;
import com.freeing.common.support.poi.excle.def.style.Font_;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.stream.Stream;

/**
 * @author yanggy
 */
public class StyleBuilder {

    public static XSSFFont buildFont(XSSFWorkbook workbook, Font_ fontX) {
        if (fontX == null) {
            return null;
        }
        // 标题字体样式
        XSSFFont font = workbook.createFont();
        // 设置字体
        if (!isEmpty(fontX.getFontName())) {
            font.setFontName(fontX.getFontName());
        }
        // 字体大小
        if (fontX.getFontHeight() > 0) {
            font.setFontHeightInPoints(fontX.getFontHeight());
        }
        // 斜体的
        font.setItalic(fontX.isItalic());
        // 删除线
        font.setStrikeout(fontX.isStrikeout());
        // 设置字体颜色
        if (!isEmpty(fontX.getColor())) {
            // reg 或者 颜色 index
            int[] colorArr = Stream.of(fontX.getColor().split(",")).mapToInt(Integer::parseInt).toArray();
            if (colorArr.length != 3 && colorArr.length != 1) {
                throw new DefinitionException("error font color: " + fontX.getColor());
            }
            if (colorArr.length == 3) {
                font.setColor(new XSSFColor(new java.awt.Color((short) colorArr[0], (short) colorArr[1], (short) colorArr[2])));
            } else {
                font.setColor((short) colorArr[0]);
            }
        }

        return font;
    }

    private static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

}
