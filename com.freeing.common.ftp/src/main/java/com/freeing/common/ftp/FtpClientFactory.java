package com.freeing.common.ftp;

public class FtpClientFactory {

    private FtpProperty ftpProperty;

    public FtpClientFactory() {

    }

    public FtpClientFactory(FtpProperty ftpProperty) {
        this.ftpProperty = ftpProperty;
    }

    public IFtpClient newClient() {
        if ("sftp".equals(ftpProperty.getProtocol())) {
            return new SftpClient(ftpProperty);
        }
        if ("ftp".equals(ftpProperty.getProtocol())) {

        }
        throw new NullPointerException("FtpClientFactory#ftpProperty is null.");
    }

    public FtpProperty getFtpProperty() {
        return ftpProperty;
    }

    public void setFtpProperty(FtpProperty ftpProperty) {
        this.ftpProperty = ftpProperty;
    }
}
