package com.freeing.common.xfile;

import com.freeing.common.xfile.config.FileStorageProperties;

public class ReTest {
    public static void main(String[] args) {
        FileStorageProperties storageProperties = new FileStorageProperties();
        FileStorageProperties.FtpsConfig ftpsConfig = new FileStorageProperties.FtpsConfig();
        ftpsConfig.setHost("192.168.134.128");
        ftpsConfig.setPort(21);
        ftpsConfig.setUsername("katou");
        ftpsConfig.setPassword("123456");
        ftpsConfig.setBasePath("/home/katou/");
        ftpsConfig.setProtocol("TLS");
    }

}
