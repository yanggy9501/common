package com.freeing.common.component.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * @author yanggy
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
    /**
     * 移动文件到目标文件夹中
     * 原理：复杂 + 删除
     *
     * @param srcDir 源文件
     * @param destDir 目标文件夹
     * @throws IOException IO异常
     */
    public static void moveFileToDirectory(File srcDir, File destDir) throws IOException {
        if (!srcDir.exists()) {
            throw new NoSuchFileException(srcDir.getAbsolutePath(), "", "The file is not existing");
        }
        if (!destDir.exists()) {
            FileUtils.forceMkdir(destDir);
        }
        if (!destDir.isDirectory()) {
            throw new NoSuchFileException(destDir.getAbsolutePath(), "", "The destDir is not directory");
        }
        copyFileToDirectory(srcDir, destDir);
        forceDelete(srcDir);
    }
}
