package com.freeing.common.storage;

import com.freeing.common.storage.config.FileStorageProperties;
import com.freeing.common.storage.factory.FtpsFileStorageClientFactory;

import java.io.*;
import java.nio.file.Path;

public class FtpsUploadTest {
    static FtpsFileStorage ftpsFileStorage = null;

    static {
        FileStorageProperties storageProperties = new FileStorageProperties();
        FileStorageProperties.FtpsConfig ftpsConfig = new FileStorageProperties.FtpsConfig();
        ftpsConfig.setHost("192.168.134.128");
        ftpsConfig.setPort(21);
        ftpsConfig.setUsername("katou");
        ftpsConfig.setPassword("123456");
        ftpsConfig.setBasePath("/home/katou/tmp");
        ftpsConfig.setProtocol("TLS");
        ftpsConfig.setImplicit(false);
        ftpsConfig.setConnectionTimeout(10 * 1000);
        ftpsConfig.setPool(new FileStorageProperties.CommonClientPoolConfig());

        FileStorageProperties.CommonClientPoolConfig poolConfig = new FileStorageProperties.CommonClientPoolConfig();
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMaxIdle(1);
        poolConfig.setMinIdle(1);
        poolConfig.setMaxTotal(1);

        ftpsConfig.setPool(poolConfig);

        FtpsFileStorageClientFactory clientFactory = new FtpsFileStorageClientFactory(ftpsConfig);

        ftpsFileStorage = new FtpsFileStorage(ftpsConfig.getBasePath(), clientFactory);
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        String path  = "E:\\Download\\Documents";
        Path of = Path.of(path);
//        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 1; i++) {
            for (File file : of.toFile().listFiles()) {
                if (file.isFile()) {
                    try (InputStream in = new FileInputStream(file)) {
                        System.out.println(file.getName());
                        ftpsFileStorage.listFiles("/");
                        ftpsFileStorage.upload(file.getName(), file);
                        System.out.println(file.getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("------------------");
    }
}
