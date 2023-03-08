package com.freeing.common.component.utils.zip;

import java.io.File;
import java.io.IOException;

/**
 * 压缩与解压工具类
 *
 * @author yanggy
 */
public class ZipUtils {
    /**
     * 解压
     *
     * @param srcFileOrDir 被解压文件
     * @param destDir 解压后文件所在目标目录
     */
    public static void decompress(File srcFileOrDir, File destDir) {

    }

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
