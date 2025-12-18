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

public record Ftps(FTPSClient client) {

    public synchronized boolean cd(String directory) {
        if (StringUtils.isBlank(directory)) {
            return true;
        }
        try {
            return client.changeWorkingDirectory(directory);
        } catch (IOException e) {
            throw new FtpException("Error dir path:" + directory, e);
        }
    }

    public void close() {
        try {
            if (client != null && client.isConnected()) {
                client.logout();
                client.disconnect();
            }
        } catch (IOException ignored) {

        }
    }

    public List<FTPFile> ls(String path) {
        try {
            FTPFile[] files = client.listFiles(path);
            if (files == null || files.length == 0) {
                return new ArrayList<>();
            }
            return new ArrayList<>(Arrays.asList(files));
        } catch (IOException e) {
            throw new FtpException("Error dir path:" + path, e);
        }
    }

    public boolean isDirExist(String remoteDir) {
        try {
            // 保存当前工作目录
            String currentDir = client.printWorkingDirectory();
            // 尝试切换目录
            boolean exists = cd(remoteDir);
            // 切回原来的工作目录
            cd(currentDir);
            return exists;
        } catch (Exception e) {
            throw new FtpException("Error dir path:" + remoteDir, e);
        }
    }

    public boolean mkdirs(String dirPath) throws Exception {
        PathUtils.standardPath(dirPath);
        String[] paths = dirPath.split("/");
        String tempPath = "";
        boolean flag = true;
        for (String p : paths) {
            if (p.isEmpty()) {
                continue;
            }
            tempPath += "/" + p;
            flag &= client.makeDirectory(tempPath);
        }
        return flag;
    }

    public boolean upload(String parentDir, String fileName, InputStream in) throws IOException {
        return client.storeFile(String.join("/", parentDir, fileName), in);
    }
    public boolean upload(String parentDir, String fileName, String localFile) throws Exception {
        try (FileInputStream fis = new FileInputStream(localFile)) {
            return client.storeFile(String.join("/", parentDir, fileName), fis);
        }
    }

    public boolean download(String remoteFile, String localFile) {
        try (FileOutputStream fos = new FileOutputStream(localFile)) {
            return client.retrieveFile(remoteFile, fos);
        } catch (IOException e) {
            throw new FtpException("Download file failed", e);
        }
    }

    public void download(String remoteFile, Consumer<InputStream> consumer) {
        try (InputStream in = client.retrieveFileStream(remoteFile);) {
            try {
                consumer.accept(in);
            } finally {
                // retrieveFileStream 是“半条命令”， 需要 completePendingCommand 客户端确认并清理状态
                client.completePendingCommand();
            }
        } catch (IOException e) {
            throw new FtpException("Download file error", e);
        }
    }
}
