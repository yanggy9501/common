package com.freeing.common.component.utils.zip;

import java.io.File;

/**
 * 解压器
 *
 * @author yanggy
 */
public class Decompressor {
    /**
     * 源压缩文件
     */
    private final File srcFile;

    /**
     * 目标目录
     */
    private final File destFile;

    private boolean mkdirStatus;

    public Decompressor(File srcFile, File destFile) {
        this.srcFile = srcFile;
        this.destFile = destFile;
    }

    public void decompress() {
        tryMkdirIfNeeded();
        doDecompress();
    }

    private void tryMkdirIfNeeded() {
        if (destFile.exists() && destFile.isDirectory()) {
            return;
        }
        mkdirStatus = destFile.mkdirs();
    }

    private void doDecompress() {

    }
}
