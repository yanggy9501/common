package com.freeing.common.xfile.util;

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
}
