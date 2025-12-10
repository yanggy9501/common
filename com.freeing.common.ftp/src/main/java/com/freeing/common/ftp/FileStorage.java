package com.freeing.common.ftp;

import com.freeing.common.ftp.ftp.Ftps;

import java.io.InputStream;
import java.util.function.Consumer;

public interface FileStorage {
    /**
     * 获取 Client ，使用完后需要归还
     */
    Ftps getClient();

    /**
     * 归还 Client
     */
    void returnClient(Ftps client);

    void close();

    String getAbsolutePath(String path);

    void upload(String parentPath, String fileName, InputStream in);

    void upload(String parentPath, String fileName, String localFile);

    void download(String remoteFile, String localFile);

    void download(String remoteFile, Consumer<InputStream> consumer);
}
