package com.freeing.common.component.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * IO 工具类
 *
 * @author yanggy
 */
public class IOUtils extends org.apache.commons.io.IOUtils {
    /**
     * 读取文本文件内容
     *
     * @param file 文件路径
     * @return String 文件内容
     * @throws IOException IOException
     */
    public static String toString(File file) throws IOException {
        if (file.isFile()) {
            return org.apache.commons.io.IOUtils.toString(new BufferedReader(new FileReader(file)));
        }
        throw new IllegalArgumentException("This is not file: " + file.getAbsolutePath());
    }
}
