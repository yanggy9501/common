package com.freeing.common.xfile.bean;

public class RemoteFile {
    private final String name;
    private final String basePath;
    private final String rootPath;

    public RemoteFile(String name, String basePath, String rootPath) {
        this.name = name;
        this.basePath = basePath;
        this.rootPath = rootPath;
    }

    public String getName() {
        return name;
    }

    public String getBasePath() {
        return basePath;
    }

    public String getRootPath() {
        return rootPath;
    }
}
