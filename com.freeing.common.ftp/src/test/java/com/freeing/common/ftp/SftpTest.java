package com.freeing.common.ftp;

import java.util.List;

/**
 * @author yanggy
 */
public class SftpTest {
    public static void main(String[] args) {
        FtpProperty ftpProperty = FtpProperty.builder("sftp", "192.168.134.128", 22, "asuna")
            .password("123456")
            .timeout(30000)
            .build();

        IFtpClient ftpClient = FtpUtil.newClient(ftpProperty);
        System.out.println(ftpClient.getRootPath());
        List<FtpFileAttrs> list = ftpClient.list(ftpClient.getRootPath() + "/a/..");
        for (FtpFileAttrs attrs : list) {
            System.out.println(attrs);
        }
//        ftpClient.deleteFile(ftpClient.getRootPath() + "/a/b/c.txt");
//        ftpClient.getFile(ftpClient.getRootPath() + "/a/b/c.txt");
//        ByteArrayInputStream inputStream = new ByteArrayInputStream("hello world".getBytes());
//        ftpClient.upload(inputStream, ftpClient.getRootPath() + "/a/b/", "test.txt");
        ftpClient.disconnect();
    }
}
