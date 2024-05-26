package com.freeing.common.support.poi.excle.def.style;

/**
 * 字体样式
 */
public class Font_ {
    /**
     * 字体类型
     */
    private String fontName;

    /**
     * 字体大小
     */
    short fontHeight;

    /**
     * 字体颜色
     */
    private String color;

    /**
     * 是否斜体
     */
    private boolean italic;

    /**
     * 是否有删除线
     */
    private boolean strikeout;

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isStrikeout() {
        return strikeout;
    }

    public void setStrikeout(boolean strikeout) {
        this.strikeout = strikeout;
    }

    public short getFontHeight() {
        return fontHeight;
    }

    public void setFontHeight(short fontHeight) {
        this.fontHeight = fontHeight;
    }
}
