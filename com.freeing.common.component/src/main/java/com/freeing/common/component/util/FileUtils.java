package com.freeing.common.component.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author yanggy
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
    /**
     * 移动文件到目标文件夹中
     * 原理：复杂 + 删除
     *
     * @param src 源文件（非目录）
     * @param dest 目标文件夹
     * @throws IOException IO异常
     */
    public static void moveFile(Path src, Path dest) throws IOException {
        Files.move(src, dest, StandardCopyOption.ATOMIC_MOVE);
    }

    public static void copy(File from, File to) {
        try (FileChannel fromChannel = new FileInputStream(from).getChannel();
             FileChannel toChannel = new FileOutputStream(to).getChannel()) {
            fromChannel.transferTo(0, fromChannel.size(), toChannel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
