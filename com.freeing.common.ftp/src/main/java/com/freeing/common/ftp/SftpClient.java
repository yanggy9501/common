package com.freeing.common.ftp;

import com.freeing.common.ftp.enums.FileType;
import com.freeing.common.ftp.exception.FtpException;
import com.jcraft.jsch.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;

/**
 * Sftp
 */
public class SftpClient extends AbstractFtpClient<ChannelSftp> {

    private FtpProperty ftpProperty;

    protected SftpClient(FtpProperty ftpProperty) {
        this.ftpProperty = ftpProperty;
        if (ftpProperty.isPassword()) {
            connectByPwd(ftpProperty.getHost(),
                ftpProperty.getPort(),
                ftpProperty.getUsername(),
                ftpProperty.getCertificate());
        } else if (ftpProperty.isPrivateKey()) {
            connectByPrk(ftpProperty.getHost(),
                ftpProperty.getPort(),
                ftpProperty.getUsername(),
                ftpProperty.getCertificate());
        } else {
            throw new FtpException("Error configuration for FtpProperty#certificate");
        }
    }

    @Override
    void connectByPrk(String host, int port, String username, String privateKeyFile) {
        try {
            JSch jsch = new JSch();
            // 设置密钥
            jsch.addIdentity(privateKeyFile);
            // 获取session
            Session session = jsch.getSession(username, host, port);
            // 不校验域名
            session.setConfig("StrictHostKeyChecking", "no");
            // 配置属性
            Properties properties = new Properties();
            // // 不校验域名
            properties.put("StrictHostKeyChecking", "no");
            session.setConfig(properties);
            // 使用会话开启连接
            if (!session.isConnected()) {
                session.connect(ftpProperty.getTimeout() > 0 ? ftpProperty.getTimeout() : 30000);
            }
            Channel channel = session.openChannel("sftp");
            if (!channel.isConnected()) {
                channel.connect(ftpProperty.getTimeout() > 0 ? ftpProperty.getTimeout() : 30000);
            }
            client = (ChannelSftp) channel;
            // 记录根目录
            this.rootPath = client.pwd();
        } catch (Exception e) {
            client = null;
            throw new FtpException(e.getMessage(), e.getCause());
        }
    }

    @Override
    void connectByPwd(String host, int port, String username, String password) {
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(username, host, port);
            // 密码
            session.setPassword(password);
            // 不校验域名
            session.setConfig("StrictHostKeyChecking", "no");
            // 默认情况下，JSch 库本身并没有会话超时时间。通常建议设置会话超时，（单位：毫秒）
            // 使用会话开启连接
            if (!session.isConnected()) {
                session.connect(ftpProperty.getTimeout() > 0 ? ftpProperty.getTimeout() : 30000);
            }

            Channel channel = session.openChannel("sftp");
            if (!channel.isConnected()) {
                channel.connect(ftpProperty.getTimeout() > 0 ? ftpProperty.getTimeout() : 30000);
            }

            client = (ChannelSftp) channel;
            this.rootPath = client.pwd();
        } catch (Exception e) {
            client = null;
            throw new FtpException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public FileType getType(String path) {
        String standardPath = standardPath(path);
        try {
            // lstat()：方法遵循符号链接 link（即返回链接的属性而不是目标）
            // stat()：方法不遵循符号链接（即返回目标的属性而不是链接）
            SftpATTRS attrs = client.lstat(standardPath);
            return getType(attrs);
        } catch (SftpException e) {
            return FileType.NOT_EXIST;
        }
    }

    private static FileType getType(SftpATTRS attrs) {
        if (attrs.isReg()) {
            return FileType.FILE;
        }
        if (attrs.isDir()) {
            return FileType.DIRECTORY;
        }
        if (attrs.isLink()) {
            return FileType.LINK;
        }
        return FileType.OTHER;
    }

    @Override
    public List<FtpAttrs> list(String dirPath) {
        String standardDirPath = standardPath(dirPath);
        // check dir
        if (FileType.DIRECTORY != getType(standardDirPath)) {
            throw new FtpException("This is not directory.");
        }
        // ls dir
        try {
            final Vector<ChannelSftp.LsEntry> lists = (Vector<ChannelSftp.LsEntry>) getClient().ls(standardDirPath);
            // 排除特殊目录：当前目录（. ） 父目录（..）
            ArrayList<FtpAttrs> lsResult = new ArrayList<>();
            for (ChannelSftp.LsEntry entry : lists) {
                String filename = entry.getFilename();
                if (".".equals(filename) || "..".equals(filename)) {
                    continue;
                }
                FtpAttrs ftpAttrs = new FtpAttrs();
                ftpAttrs.setFilename(filename);

                SftpATTRS sftpATTRS = entry.getAttrs();
                ftpAttrs.setSize(sftpATTRS.getSize());
                ftpAttrs.setParentPath(standardDirPath);

                Instant instant = Instant.ofEpochSecond(Integer.toUnsignedLong(sftpATTRS.getMTime()));
                ftpAttrs.setLastUpdateTime(Date.from(instant));

                ftpAttrs.setType(getType(sftpATTRS));

                // 文件扩展名
                if (ftpAttrs.getType() == FileType.FILE) {
                    // 获取扩展名
                    int lastIndexOf = filename.lastIndexOf(".");
                    if (lastIndexOf > 0) {
                        ftpAttrs.setExtension(filename.substring(lastIndexOf));
                    }
                }

                lsResult.add(ftpAttrs);
            }
            return lsResult;
        } catch (Exception e) {
            throw new FtpException("Error list dir.", e);
        }
    }

    @Override
    public void disconnect() {
        try {
            if (client.isConnected()) {
                client.getSession().disconnect();
                client.disconnect();
                client.quit();
            }
        } catch (Exception e) {
            throw new FtpException("Disconnect sftp client error!", e);
        }
    }

    @Override
    public void changeDirectory(String dirPath) {
        String standardPath = standardPath(dirPath);
        if (standardPath.isEmpty()) {
            throw new FtpException("Error dir path:" + "" + dirPath + "");
        }
        try {
            client.cd(standardPath);
        } catch (Exception e) {
            throw new FtpException("Fail to cd to target directory");
        }
    }

    @Override
    public byte[] readFile(String filePath) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream; // 字节流不需要关闭
        String standardPath = standardPath(filePath);
        if (getType(standardPath) != FileType.FILE) {
            throw new FtpException("This is not file, not support");
        }

        String fileName;
        String parentDir;
        int idx = standardPath.lastIndexOf("/");
        if (idx == -1) {
            if (standardPath.length() > 0) {
                fileName = standardPath;
            } else {
                throw new FtpException("没有输入文件路径");
            }
            parentDir = rootPath;
        } else {
            fileName = standardPath.substring(idx + 1);
            parentDir = standardPath.substring(0, idx);
        }

        try {
            changeDirectory(parentDir);
            inputStream = client.get(fileName);

            outputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[4096];
            int size;
            while ((size = inputStream.read(bytes)) > 0) {
                outputStream.write(bytes, 0, size);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new FtpException("Fail to read file.");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {

                }
            }
        }
    }

    @Override
    public InputStream getFile(String filePath) {
        String standardPath = standardPath(filePath);
        if (getType(standardPath) != FileType.FILE) {
            throw new FtpException("This is not file, not support");
        }

        String fileName;
        String parentDir;
        int idx = standardPath.lastIndexOf("/");
        if (idx == -1) { // 从根路径下载
            if (standardPath.length() > 0) {
                fileName = standardPath;
            } else {
                throw new FtpException("Error file path");
            }
            parentDir = rootPath;
        } else {
            fileName = standardPath.substring(idx + 1);
            parentDir = standardPath.substring(0, idx);
        }

        try {
            // 切换到文件目录
            changeDirectory(parentDir);

            return client.get(fileName);
        } catch (Exception e) {
            throw new FtpException("Get file inputStream error", e);
        }
    }

    @Override
    public void upload(InputStream uploadIn, String remoteDir, String filename) {
        if (uploadIn == null) {
            return;
        }
        try {
            // 切换到目标路径
            changeDirectory(remoteDir);
            client.put(uploadIn, filename);
        } catch (Exception e) {
            throw new FtpException("Fila to upload", e);
        } finally {
            if (uploadIn != null) {
                try {
                    uploadIn.close();
                } catch (IOException ignored) {

                }
            }
        }
    }

    @Override
    public void deleteFile(String filePath) {
        String standardPath = standardPath(filePath);
        if (getType(standardPath) != FileType.FILE) {
            throw new FtpException("This is not file, Not supported del");
        }
        String fileName;
        int idx = standardPath.lastIndexOf("/");
        if (idx == -1) {
            if (standardPath.length() > 0) {
                fileName = standardPath;
            } else {
                throw new FtpException("Error file path");
            }
            changeDirectory(rootPath);

        } else {
            fileName = standardPath.substring(idx + 1);
            String parentDir = standardPath.substring(0, idx);
            changeDirectory(parentDir);
        }
        try {
            client.rm(fileName);
        } catch (SftpException e) {
            throw new FtpException("Fail to del file");
        }
    }
}
