package com.freeing.common.component.util.zip;

import java.io.File;
import java.io.IOException;

/**
 * 压缩与解压工具类
 *
 * @author yanggy
 */
public class ZipUtils {
    /**
     * 压缩文件或目录
     *
     * @param srcFileOrDir 被压缩文件或目录
     * @param destDir 压缩后压缩文件所在目录
     * @param zipFileName 压缩后压缩文件命令
     */
    public static void compress(File srcFileOrDir, File destDir, String zipFileName) throws IOException {
        Compressor compressor = new Compressor(srcFileOrDir, destDir, zipFileName);
        compressor.compress();
    }
}
