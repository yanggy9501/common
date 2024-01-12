package com.freeing.common.component.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 文件类型判断工具类
 *
 * @author yanggy
 */
public class FileTypeUtils {

    /**
     * 判断文件类型
     *
     * @param file 文件
     * @return FileType 文件类型
     * @throws IOException
     */
    public static FileType getType(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("file is not exists, and file path is " + file.getAbsolutePath());
        }
        if (file.isDirectory()) {
            throw new FileNotFoundException(file.getAbsolutePath() + "is directory, instead of file.");
        }
        String fileHeader = getFileHeader(file);
        return FileType.match(fileHeader);
    }

    /**
     *
     * @param file 文件
     * @return 文件头 16 进制形式
     * @throws IOException
     */
    private static String getFileHeader(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        byte[] fileHeaderBytes = new byte[28];
        try {
            in.read(fileHeaderBytes, 0, 28);
        } finally {
            in.close();
        }
        return bytesToHexString(fileHeaderBytes);
    }

    /**
     * 将文件头（文件类型）转换危机16进制形式字符串
     *
     * @param bytes
     * @return
     */
    private static String bytesToHexString(byte[] bytes) {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String hv;
        int iv;
        for (byte bt : bytes) {
            // 一byte 8 bit 与二进制 1111 1111 与运算, 4 bit 为一位十六进制
            iv = bt & 0xFF;
            hv = Integer.toHexString(iv);
            if (hv.length() < 2) {
                sb.append(0);
            }
            sb.append(hv);
        }
        return sb.toString();
    }
}
