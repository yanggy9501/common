package com.freeing.common.ftp;

import com.freeing.common.ftp.enums.FileType;
import com.jcraft.jsch.ChannelSftp;

import java.io.InputStream;
import java.util.List;

/**
 * ftp
 */
public class FtpClient extends AbstractFtpClient<ChannelSftp> {

    private static final int DEFAULT_TIMEOUT = 30000;

//    private final FtpProperty ftpProperty;

    @Override
    protected void connectByPrk(String host, int port, String username, String privateKeyFile) {

    }

    @Override
    protected void connectByPwd(String host, int port, String username, String password) {

    }

    @Override
    protected void changeDirectory(String dirPath) {

    }

    @Override
    protected void makeDirectory(String dirPath) {

    }

    @Override
    public FileType getType(String path) {
        return null;
    }

    @Override
    public List<FtpFileAttrs> list(String dirPath) {
        return null;
    }

    @Override
    public void disconnect() {

    }

    @Override
    public byte[] readFile(String filePath) {
        return new byte[0];
    }

    @Override
    public InputStream getFile(String filePath) {
        return null;
    }

    @Override
    public void upload(InputStream uploadIn, String remoteDir, String filename) {

    }

    @Override
    public void deleteFile(String filePath) {

    }
}
