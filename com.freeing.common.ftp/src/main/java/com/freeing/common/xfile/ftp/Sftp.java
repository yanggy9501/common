package com.freeing.common.xfile.ftp;

import com.freeing.common.xfile.exception.FtpException;
import com.jcraft.jsch.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.function.Consumer;

public class Sftp extends AbstractClient {
    private Session session;
    private ChannelSftp client;

    public Sftp(Session session, int timeout, Charset charset) {
        init(session, timeout, charset);
    }

    private void init(Session session, int timeout, Charset charset) {
        this.session = session;
        ChannelSftp channel;
        try {
            if (!session.isConnected()) {
                session.connect();
            }
            channel = (ChannelSftp) session.openChannel("sftp");
        } catch (JSchException e) {
            throw new FtpException(e);
        }
        try {
            channel.connect(Math.max(timeout, 0));
        } catch (JSchException e) {
            throw new FtpException(e);
        }
        channel.setFilenameEncoding(charset);
        this.client = channel;
    }

    @Override
    public boolean cd(String directory) {
        return false;
    }

    @Override
    public String pwd() {
        try {
            return getClient().pwd();
        } catch (SftpException e) {
            throw new FtpException(e);
        }
    }

    @Override
    public boolean isDir(String dir) {
        return super.isDir(dir);
    }

    @Override
    public boolean mkdir(String dir) {
        if (isDir(dir)) {
            // 目录已经存在，创建直接返回
            return true;
        }
        try {
            getClient().mkdir(dir);
            return true;
        } catch (SftpException e) {
            throw new FtpException(e);
        }
    }

    @Override
    public List<String> ls(String path) {
        return null;
    }

    @Override
    public boolean delFile(String path) {
        try {
            getClient().rm(path);
            return true;
        } catch (SftpException e) {
            throw new FtpException(e);
        }
    }

    @Override
    public boolean delDir(String dirPath) {
        return false;
    }

    @Override
    public boolean upload(String destPath, File file) throws IOException {
        return false;
    }

    @Override
    public void download(String path, File outFile) {

    }

    @Override
    public void download(String remoteFile, Consumer<InputStream> consumer) {

    }

    @Override
    public void close() throws IOException {

    }

    /**
     * 获取SFTP通道客户端
     *
     * @return 通道客户端
     */
    public ChannelSftp getClient() {
        return this.client;
    }
}
