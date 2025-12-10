package com.freeing.common.ftp.ftp;

import com.freeing.common.ftp.exception.FtpException;
import com.freeing.common.ftp.util.PathUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPSClient;

import java.io.*;
import java.util.function.Consumer;

public record Ftps(FTPSClient client) {

    synchronized public boolean cd(String directory) {
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

    public boolean download(String remoteFile, Consumer<InputStream> consumer) {
        InputStream in;
        try {
            in = client.retrieveFileStream(remoteFile);
        } catch (IOException e) {
            throw new FtpException("Download file failed", e);
        }
        consumer.accept(in);
        return true;
    }

    public void listFiles(String remotePath) throws Exception {
        FTPFile[] files = client.listFiles(remotePath);
        for (FTPFile file : files) {
            System.out.println(file);
        }
    }
}
