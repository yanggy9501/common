package com.freeing.common.storage;

import com.freeing.common.storage.bean.RemoteFile;

import java.io.File;
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

    List<RemoteFile> listFiles(String path);

    List<RemoteFile> listDirs(String path);

    void upload(String destPath, InputStream in);

    void upload(String destPath, File file);

    void download(String destPath, File file);

    void download(String destPath, Consumer<InputStream> consumer);
}
