package com.freeing.common.xfile;

import com.freeing.common.xfile.bean.RemoteFile;

import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;

public interface FileStorage<T> {
    /**
     * 获取 Client ，使用完后需要归还
     */
    T getClient();

    /**
     * 归还 Client
     */
    void returnClient(T client);

    void close();

    String getAbsolutePath(String basePath, String path);

    List<RemoteFile> listFiles(String parentPath);

    void upload(String parentPath, String fileName, InputStream in);

    void upload(String parentPath, String fileName, String localFile);

    void download(String remoteFile, String localFile);

    void download(String remoteFile, Consumer<InputStream> consumer);
}
