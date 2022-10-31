package com.freeing.common.component.utils.zip;

import com.freeing.common.component.constants.NumConstants;

import java.io.*;
import java.security.AccessControlException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 压缩解压器
 *
 * @author yanggy
 */
class Compressor {
    /**
     * 被压缩文件或目录
     */
    private final File srcFile;

    /**
     * 压缩到目标目录
     */
    private final File destFile;

    /**
     * 目标目录存在状态，true：压缩前就存在 false：压缩前就不存在
     */
    private boolean destFileExisting;

    /**
     * 压缩后压缩文件名
     */
    private final String fileName;

    public Compressor(File srcFile, File destFile, String fileName) {
        this.srcFile = srcFile;
        this.destFile = destFile;
        this.fileName = fileName;
    }

    void compress() throws IOException {
        checkDestFileIsLegal();
        tryMkdirIfNeeded(destFile);
        ZipOutputStream zipOut = null;
        BufferedOutputStream bufferedOut = null;
        FileOutputStream out = null;
        try {
            // 压缩文件绝对路径
            String destFilePath = destFile.getAbsolutePath() + File.separator + fileName;
            out = new FileOutputStream(destFilePath);
            zipOut = new ZipOutputStream(out);
            bufferedOut = new BufferedOutputStream(zipOut);

            // 压缩
            doCompress(zipOut, srcFile, srcFile.getName(), bufferedOut);
        } catch (IOException ignored) {
            // 压缩失败时删除创建的空目录
            if (!destFileExisting && destFile.exists() && destFile.isDirectory()) {
                boolean ignore = destFile.delete();
            }
        } finally {
            close(out, zipOut, bufferedOut);
        }
    }

    private void close(FileOutputStream out, ZipOutputStream zipOut, BufferedOutputStream bufferedOut)
            throws IOException {
        // close stream，注意关流的顺序，从外往内
        if (bufferedOut != null) {
            bufferedOut.close();
        }
        if (zipOut != null) {
            zipOut.close();
        }
        if (out != null) {
            out.close();
        }
    }

    private void checkDestFileIsLegal() {
        if (destFile.getAbsolutePath().startsWith(srcFile.getAbsolutePath())) {
            throw new IllegalArgumentException("Error target zip file path.");
        }
    }

    private void tryMkdirIfNeeded(File file) {
        if (file.exists() && file.isDirectory()) {
            destFileExisting = true;
            return;
        }
        if (!file.mkdirs()) {
            // 无法创建时抛出异常，如：无创建权限
            throw new AccessControlException("Can not create dir.");
        }
    }

    private void doCompress(ZipOutputStream zipOut, File srcFile, String name, BufferedOutputStream bufferedOut)
        throws IOException {
        if (srcFile.isDirectory()) {
            File[] files = srcFile.listFiles();
            // 处理空目录
            if (files == null || files.length == 0) {
                zipOut.putNextEntry(new ZipEntry(name + File.separator));
                return;
            }
            for (File file : files) {
                doCompress(zipOut, file, name + File.separator + file.getName(), bufferedOut);
            }
        } else {
            zipOut.putNextEntry(new ZipEntry(name));
            FileInputStream in = new FileInputStream(srcFile);
            BufferedInputStream bufferedIn = new BufferedInputStream(in);
            byte[] buffer = new byte[NumConstants.BUFFER_DEFAULT_SIZE];
            int len;
            while ((len = bufferedIn.read(buffer)) > 0) {
                bufferedOut.write(buffer, 0, len);
            }
            bufferedIn.close();
            in.close();
        }
    }
}
