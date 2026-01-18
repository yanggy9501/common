package com.freeing.common.storage;

import com.freeing.common.storage.bean.RemoteFile;
import com.freeing.common.storage.factory.SftpFileStorageClientFactory;
import com.freeing.common.storage.ftp.Sftp;
import com.freeing.common.storage.util.PathUtils;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class SftpFileStorage implements FileStorage<Sftp> {
    private final String basePath;
    private final SftpFileStorageClientFactory clientFactory;

    public SftpFileStorage(String basePath, SftpFileStorageClientFactory clientFactory) {
        this.basePath = basePath;
        this.clientFactory = clientFactory;
    }

    /**
     * 获取 Client ，使用完后需要归还
     */
    @Override
    public Sftp getClient() {
        return null;
    }

    /**
     * 归还 Client
     */
    @Override
    public void returnClient(Sftp client) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getAbsolutePath(String basePath, String path) {
        return PathUtils.standardPath(basePath + "/" + path);
    }

    private <T> T executeWithClient(Function<Sftp, T> operation) {
        Sftp ftps = getClient();
        try {
            return operation.apply(ftps);
        } finally {
            returnClient(ftps);
        }
    }

    @Override
    public List<RemoteFile> listFiles(String path) {
        return null;
    }

    @Override
    public List<RemoteFile> listDirs(String path) {
        return null;
    }

    @Override
    public void upload(String destPath, InputStream in) {

    }

    @Override
    public void upload(String destPath, File file) {

    }

    @Override
    public void download(String destPath, File file) {

    }

    @Override
    public void download(String destPath, Consumer<InputStream> consumer) {

    }
}
