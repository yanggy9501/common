package com.freeing.common.ftp;

import java.util.List;

/**
 * @author yanggy
 */
public class SftpTest {
    public static void main(String[] args) {
        testHr();
    }

    public static void testHr() {
        FtpProperty ftpProperty = FtpProperty.builder("sftp", "dropzone.paypal.cn", 22, "batch_huarun_exchange")
            .privateKeyFile("123456")
            .timeout(30000)
            .build();
        IFtpClient ftpClient = FtpUtil.newClient(ftpProperty);
        List<FtpAttrs> list = ftpClient.list("/root");
        for (FtpAttrs attrs : list) {
            System.out.println(attrs);
        }

        ftpClient.disconnect();
    }
}
