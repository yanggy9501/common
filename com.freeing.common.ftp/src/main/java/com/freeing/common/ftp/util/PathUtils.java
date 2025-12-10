package com.freeing.common.ftp.util;

public class PathUtils {
    /**
     * 标准化路径
     *
     * @param aPath 文件路径
     */
    public static String standardPath(String aPath) {
        String path = aPath == null ? "" : aPath.trim();
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
