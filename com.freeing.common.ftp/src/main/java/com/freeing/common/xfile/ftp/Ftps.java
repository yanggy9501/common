package com.freeing.common.xfile.ftp;

import com.freeing.common.xfile.exception.FtpException;
import com.freeing.common.xfile.util.PathUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Ftps extends AbstractClient {

    private final FTPSClient client;

    public Ftps(FTPSClient client) {
        this.client = client;
    }

    public FTPSClient client() {
        return client;
    }

    @Override
    public boolean cd(String directory) {
        synchronized (client) {
            if (StringUtils.isBlank(directory)) {
                return true;
            }
            try {
                return client.changeWorkingDirectory(directory);
            } catch (IOException e) {
                throw new FtpException("Error dir path:" + directory, e);
            }
        }
    }

    @Override
    public String pwd() {
        try {
            return client.printWorkingDirectory();
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    @Override
    public boolean mkdir(String dir) {
        if (isDir(dir)) {
            return true;
        }
        try {
            return client.makeDirectory(dir);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    @Override
    public boolean delFile(String path) {
        try {
            return client.deleteFile(path);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    @Override
    public boolean delDir(String dirPath) {
        try {
            return client.removeDirectory(dirPath);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    public boolean upload(String destPath, InputStream in) {
        try {
            return client.storeFile(PathUtils.standardPath(destPath), in);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    public boolean upload(String destPath, File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            return client.storeFile(PathUtils.standardPath(destPath), fis);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }

    @Override
    public void download(String path, File outFile) {
        try (FileOutputStream fos = new FileOutputStream(outFile)) {
            client.retrieveFile(path, fos);
        } catch (IOException e) {
            throw new FtpException(e);
        }
    }



    @Override
    public void download(String remoteFile, Consumer<InputStream> consumer) {
        try (InputStream in = client.retrieveFileStream(remoteFile);) {
            consumer.accept(in);
        } catch (IOException e) {
            throw new FtpException(e);
        } finally {
            try {
                // retrieveFileStream 是“半条命令”， 需要 completePendingCommand 确认并清理状态
                client.completePendingCommand();
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void close() {
        try {
            if (client != null && client.isConnected()) {
                client.logout();
                client.disconnect();
            }
        } catch (IOException ignored) {

        }
    }

    @Override
    public List<String> ls(String path) {
        try {
            FTPFile[] files = client.listFiles(path);
            if (files == null || files.length == 0) {
                return new ArrayList<>();
            }
            List<String> names = new ArrayList<>();
            for (FTPFile file : files) {
                names.add(file.getName());
            }
            return names;
        } catch (IOException e) {
            throw new FtpException("Error dir path:" + path, e);
        }
    }

    public List<FTPFile> ll(String path) {
        try {
            ArrayList<FTPFile> list = new ArrayList<>();
            FTPFile[] files = client.listFiles(path);
            if (files == null || files.length == 0) {
                return list;
            }
            return new ArrayList<>(Arrays.asList(files));
        } catch (IOException e) {
            throw new FtpException("Error dir path:" + path, e);
        }
    }
}
