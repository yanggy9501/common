package com.freeing.common.component.utils;

import com.freeing.common.component.constants.NumConstants;

import java.io.*;
import java.security.AccessControlException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

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
    public static void compress(File srcFileOrDir, String destDir, String zipFileName) throws IOException {
        checkZipFilePath(srcFileOrDir.getAbsolutePath(), destDir);
        tryMkdirIfNeeded(destDir);
        // 压缩文件绝对路径
        String targetFile = destDir + File.separator + zipFileName;
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(targetFile));
        BufferedOutputStream bufferedOut = new BufferedOutputStream(zipOut);
        // 压缩
        doCompress(zipOut, srcFileOrDir, srcFileOrDir.getName(), bufferedOut);
        // close stream
        bufferedOut.close();
        zipOut.close();
    }

    /**
     * 检查缩后压缩文件所在目录的合法性
     * 注意：缩后压缩文件所在目录不能是在被压缩源所在目录或子目录
     *
     * @param srcFileOrDirPath 被压缩文件或目录
     * @param destDirPath 缩后压缩文件所在目录
     */
    private static void checkZipFilePath(String srcFileOrDirPath, String destDirPath) {
        if (srcFileOrDirPath.startsWith(destDirPath)) {
            throw new IllegalArgumentException("Illegal zip path: " + destDirPath);
        }
    }

    private static void tryMkdirIfNeeded(String dir) {
        File file = new File(dir);
        if (file.exists() && file.isDirectory()) {
            return;
        }
        if (!file.mkdirs()) {
            // 无法创建时抛出异常，如：无创建权限
            throw new AccessControlException("Can not create dir: " + file.getAbsolutePath());
        }
    }

    private static void doCompress(ZipOutputStream zipOut, File srcFile, String name, BufferedOutputStream bufferedOut)
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
