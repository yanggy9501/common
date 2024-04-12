package com.freeing.common.ftp;

import com.freeing.common.ftp.attr.Attrs;

import java.util.List;

/**
 * @author yanggy
 */
public class SftpTest {
    public static void main(String[] args) {
        FtpProperty ftpProperty = FtpProperty.builder("sftp", "192.168.134.128", 22, "root")
            .password("123456")
            .timeout(30000)
            .build();
        IFtpClient ftpClient = FtpUtil.newClient(ftpProperty);
        List<Attrs> list = ftpClient.list("/root");
        for (Attrs attrs : list) {
            System.out.println(attrs);
        }

        ftpClient.disconnect();
    }
}
