package com.freeing.common.xfile;

import com.freeing.common.xfile.config.FileStorageProperties;
import com.freeing.common.xfile.exception.FtpException;
import com.freeing.common.xfile.factory.FtpsFileStorageClientFactory;
import org.apache.commons.net.ftp.FTPFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class FtpsTest {

    public static void main(String[] args) throws InterruptedException {
        FileStorageProperties storageProperties = new FileStorageProperties();
        FileStorageProperties.FtpsConfig ftpsConfig = new FileStorageProperties.FtpsConfig();
        ftpsConfig.setHost("192.168.134.128");
        ftpsConfig.setPort(21);
        ftpsConfig.setUsername("katou");
        ftpsConfig.setPassword("123456");
        ftpsConfig.setBasePath("/home/katou/");
        ftpsConfig.setProtocol("TLS");
        ftpsConfig.setImplicit(false);
        ftpsConfig.setConnectionTimeout(10 * 1000);
        ftpsConfig.setPool(new FileStorageProperties.CommonClientPoolConfig());

        FileStorageProperties.CommonClientPoolConfig poolConfig = new FileStorageProperties.CommonClientPoolConfig();
        poolConfig.setTestOnBorrow( true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMaxIdle(1);
        poolConfig.setMinIdle(1);
        poolConfig.setMaxTotal(1);

        ftpsConfig.setPool(poolConfig);

        FtpsFileStorageClientFactory clientFactory = new FtpsFileStorageClientFactory(ftpsConfig);

        FtpsFileStorage ftpsFileStorage = new FtpsFileStorage(ftpsConfig.getBasePath(), clientFactory);

        for (int i = 0; i < 2; i++) {
            List<FTPFile> ftpFiles1 = ftpsFileStorage.listFiles("/ftps-test/aml");
            System.out.println("ftpFiles1(/ftps-test/aml): " + ftpFiles1.size());
        }

        List<FTPFile> ftpFiles = ftpsFileStorage.listFiles("/ftps-test");
        System.out.println("ftpFiles: " + ftpFiles.size());
        System.out.println(ftpFiles);
        for (FTPFile ftpFile : ftpFiles) {
            System.out.println(ftpFile.getName());
            ftpsFileStorage.download("/ftps-test/" + ftpFile.getName(), (in) -> {
                try {
                    System.out.println("download");
                    Files.copy(in, Path.of("E:\\" + ftpFile.getName()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new FtpException("Download file failed", e);
                }
            });
        }

        System.out.println("==========================================================");

        Thread.sleep(2000);

        for (int i = 0; i < 2; i++) {
            List<FTPFile> ftpFiles2 = ftpsFileStorage.listFiles("/ftps-test/aml");
            System.out.println("ftpFiles1(/ftps-test/aml): " + ftpFiles2.size());
        }

        for (int i = 0; i < 2; i++) {
            List<FTPFile> ftpFiles3 = ftpsFileStorage.listFiles("/ftps-test/aml");
            System.out.println("ftpFiles1(/ftps-test/aml): " + ftpFiles3.size());
        }

//        new Thread(() -> {
//            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//            List<FTPFile> ftpFiles2 = ftpsFileStorage.listFiles("/ftps-test/aml");
//            System.out.println("ftpFiles2: " + ftpFiles2.size());
//            System.out.println(ftpFiles2);
//            for (FTPFile ftpFile : ftpFiles2) {
//                System.out.println(ftpFile.getName());
//                ftpsFileStorage.download("/ftps-test/aml" + ftpFile.getName(), "E:\\" + ftpFile.getName());
//            }
////        }).start();

    }

}
