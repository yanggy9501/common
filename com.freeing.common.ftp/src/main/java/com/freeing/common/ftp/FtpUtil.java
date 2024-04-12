package com.freeing.common.ftp;

/**
 * @author yanggy
 */
public class FtpUtil {

    public static IFtpClient newClient(FtpProperty ftpProperty) {
        if ("sftp".equals(ftpProperty.getProtocol())) {
            return new SftpClient(ftpProperty);
        }
        return null;
    }
}
