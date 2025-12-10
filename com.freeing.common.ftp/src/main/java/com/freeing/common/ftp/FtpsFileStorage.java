package com.freeing.common.ftp;

import com.freeing.common.ftp.config.FileStorageProperties;
import com.freeing.common.ftp.exception.FtpException;
import com.freeing.common.ftp.factory.FtpsFileStorageClientFactory;
import com.freeing.common.ftp.ftp.Ftps;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Consumer;

public class FtpsFileStorage implements FileStorage {
    private final String basePath;
    private final FtpsFileStorageClientFactory clientFactory;

    public FtpsFileStorage(String basePath, FtpsFileStorageClientFactory clientFactory) {
        this.basePath = basePath;
        this.clientFactory = clientFactory;
    }

    /**
     * 获取 Client ，使用完后需要归还
     */
    public Ftps getClient() {
        return clientFactory.getClient();
    }

    /**
     * 归还 Client
     */
    public void returnClient(Ftps client) {
        clientFactory.returnClient(client);
    }

    public void close() {
        clientFactory.close();
    }

    public String getAbsolutePath(String path) {
        return basePath + path;
    }

    public void upload(String parentPath, String fileName, InputStream in) throws IOException {
        Ftps ftps = getClient();
        String absolutePath = getAbsolutePath(parentPath);
        try {
            boolean upload = ftps.upload(absolutePath, fileName, in);
            if (!upload) {
                throw new FtpException("Upload file failed");
            }
        } finally {
            returnClient(ftps);
        }
    }

    public void upload(String parentPath, String fileName, String localFile) throws Exception {
        Ftps ftps = getClient();
        String absolutePath = getAbsolutePath(parentPath);
        try {
            boolean upload = ftps.upload(absolutePath, fileName, localFile);
            if (!upload) {
                throw new FtpException("Upload file failed");
            }
        } finally {
            returnClient(ftps);
        }
    }

    public void download(String remoteFile, String localFile) {
        Ftps ftps = getClient();
        String absolutePath = getAbsolutePath(remoteFile);
        try {
            boolean download = ftps.download(absolutePath, localFile);
            if (!download) {
                throw new FtpException("Download file failed");
            }
        } finally {
            returnClient(ftps);
        }
    }

    public void download(String remoteFile, Consumer<InputStream> consumer) {
        Ftps ftps = getClient();
        String absolutePath = getAbsolutePath(remoteFile);
        try {
            ftps.download(absolutePath, consumer);
        } finally {
            returnClient(ftps);
        }
    }


    public static void main(String[] args) throws Exception {
        FileStorageProperties.FtpsConfig ftpsConfig = new FileStorageProperties.FtpsConfig();
        ftpsConfig.setHost("192.168.134.128");
        ftpsConfig.setPort(21);
        ftpsConfig.setUsername("katou");
        ftpsConfig.setPassword("123456");
        ftpsConfig.setBasePath("/home/katou");
        ftpsConfig.setProtocol("TLS");
        ftpsConfig.setImplicit(false);
        ftpsConfig.setConnectionTimeout(10 * 1000);
        ftpsConfig.setPool(new FileStorageProperties.CommonClientPoolConfig());
        FtpsFileStorageClientFactory client = new FtpsFileStorageClientFactory(ftpsConfig);

        Ftps ftps = client.getClient();

        FtpsFileStorage ftpsFileStorage = new FtpsFileStorage(ftpsConfig.getBasePath(), client);

        FileInputStream fileInputStream = new FileInputStream(new File("E:\\重试.txt"));

        ftpsFileStorage.upload("/", "test2.txt", fileInputStream);
        ftpsFileStorage.upload("/", "test.txt", "E:\\settings.xml");
        fileInputStream.close();

    }
}
