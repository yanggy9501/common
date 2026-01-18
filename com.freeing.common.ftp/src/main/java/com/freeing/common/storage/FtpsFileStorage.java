package com.freeing.common.storage;

import com.freeing.common.constants.StrPool;
import com.freeing.common.storage.bean.RemoteFile;
import com.freeing.common.storage.enums.FileType;
import com.freeing.common.storage.exception.FtpException;
import com.freeing.common.storage.factory.FtpsFileStorageClientFactory;
import com.freeing.common.storage.ftp.Ftps;
import com.freeing.common.storage.util.PathUtils;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class FtpsFileStorage implements FileStorage<Ftps> {
    private final String basePath;
    private final FtpsFileStorageClientFactory clientFactory;

    public FtpsFileStorage(String basePath, FtpsFileStorageClientFactory clientFactory) {
        this.basePath = basePath;
        this.clientFactory = clientFactory;
    }

    /**
     * 获取 Client ，使用完后需要归还
     */
    @Override
    public Ftps getClient() {
        return clientFactory.getClient();
    }

    /**
     * 归还 Client
     */
    @Override
    public void returnClient(Ftps client) {
        clientFactory.returnClient(client);
    }

    @Override
    public void close() {
        clientFactory.close();
    }

    @Override
    public String getAbsolutePath(String basePath, String path) {
        return PathUtils.standardPath(basePath + StrPool.FILE_SEPARATOR + path);
    }

    private <T> T executeWithClient(Function<Ftps, T> operation) {
        Ftps ftps = getClient();
        try {
            return operation.apply(ftps);
        } finally {
            returnClient(ftps);
        }
    }

    private void executeWithClientVoid(Consumer<Ftps> operation) {
        Ftps ftps = getClient();
        try {
            operation.accept(ftps);
        } catch (Exception e) {
            throw new FtpException("FTPS Operation error", e);
        } finally {
            returnClient(ftps);
        }
    }

    @Override
    public List<RemoteFile> listFiles(String path) {
        String absolutePath = getAbsolutePath(this.basePath, path);
        return executeWithClient(ftps ->
            ftps.ll(absolutePath).stream()
                .filter(FTPFile::isFile)
                .map(ftpFile -> new RemoteFile(FileType.FILE.name(), ftpFile.getName(), path, absolutePath))
                .toList()
        );
    }

    @Override
    public List<RemoteFile> listDirs(String path) {
        String absolutePath = getAbsolutePath(this.basePath, path);
        return executeWithClient(ftps ->
            ftps.ll(absolutePath).stream()
                .filter(FTPFile::isDirectory)
                .map(ftpFile -> new RemoteFile(FileType.DIR.name(), ftpFile.getName(), path, absolutePath))
                .toList());
    }

    @Override
    public void upload(String destPath, InputStream in) {
        executeWithClient(ftps -> {
            String absolutePath = getAbsolutePath(this.basePath, destPath);
            try {
                boolean upload = ftps.upload(absolutePath, in);
                if (!upload) {
                    throw new FtpException("Upload file failed");
                }
            } catch (Exception e) {
                throw new FtpException("Upload file error", e);
            }
            return null;
        });

    }

    @Override
    public void upload(String destPath, File file) {
        executeWithClient(ftps -> {
            String absolutePath = getAbsolutePath(this.basePath, destPath);
            try {
                boolean upload = ftps.upload(absolutePath, file);
                if (!upload) {
                    throw new FtpException("Upload file failed");
                }
            } catch (Exception e) {
                throw new FtpException("Upload file error", e);
            }
            return null;
        });
    }

    @Override
    public void download(String remoteFile, File outFile) {
        Ftps ftps = getClient();
        String absolutePath = getAbsolutePath(this.basePath, remoteFile);
        try {
            ftps.download(absolutePath, outFile);
        } finally {
            returnClient(ftps);
        }
    }

    @Override
    public void download(String remoteFile, Consumer<InputStream> consumer) {
        Ftps ftps = getClient();
        String absolutePath = getAbsolutePath(this.basePath, remoteFile);
        try {
            ftps.download(absolutePath, consumer);
        } finally {
            returnClient(ftps);
        }
    }
}
