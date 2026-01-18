package com.freeing.common.storage.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtils {
    /**
     * 标准化路径
     *
     * @param path 文件路径
     * @return 标准路径，如：/a/b/c 或 a/b/c
     */
    public static String standardPath(String path) {
        path = path == null ? "" : path.trim();
        if (path.isEmpty()) {
            return "";
        }

        if (path.equals("/")) {
            return path;
        }

        // 反斜杠"\\"转正"/"
        path = path.replaceAll("\\\\", "/");
        // 双正斜杠"//"去重 "/"
        while (path.contains("//")) {
            path = path.replaceAll("//", "/");
        }

        // 去掉结尾 "/"
        if (path.endsWith("/")) {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * 获取子目录相对于父目录的路径
     *
     * @param parentDir 父目录路径
     * @param childDir  子目录路径
     * @return 相对路径（如：src/main/java）
     * @throws IllegalArgumentException 如果 childDir 不是 parentDir 的子路径
     */
    public static String getRelativePath(String parentDir, String childDir) {
        if (parentDir == null || childDir == null) {
            throw new IllegalArgumentException("Path must be not empty.");
        }
        Path parentPath = Paths.get(parentDir).normalize();
        Path childPath = Paths.get(childDir).normalize();

        if (!childPath.startsWith(parentPath)) {
            throw new IllegalArgumentException("Both path is not Father-son relationship.");
        }
        return parentPath.relativize(childPath).toString();
    }
}
