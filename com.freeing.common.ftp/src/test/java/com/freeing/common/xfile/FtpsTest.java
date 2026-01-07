package com.freeing.common.xfile;

import com.freeing.common.xfile.bean.RemoteFile;
import com.freeing.common.xfile.config.FileStorageProperties;
import com.freeing.common.xfile.factory.FtpsFileStorageClientFactory;
import com.freeing.common.xfile.util.PathUtils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class FtpsTest {
    static FtpsFileStorage ftpsFileStorage = null;

    static {
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
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMaxIdle(10);
        poolConfig.setMinIdle(10);
        poolConfig.setMaxTotal(10);

        ftpsConfig.setPool(poolConfig);

        FtpsFileStorageClientFactory clientFactory = new FtpsFileStorageClientFactory(ftpsConfig);

        ftpsFileStorage = new FtpsFileStorage(ftpsConfig.getBasePath(), clientFactory);
    }

    public static void main(String[] args) throws InterruptedException {

        LinkedList<String> dirList = new LinkedList<>();

        dirList.add(inputPath);
        while (!dirList.isEmpty()) {
            String id = UUID.randomUUID().toString().replaceAll("-", "");
            // 查文件夹
            String dir = dirList.removeFirst();
            // 该目录中子目录: listChildrenDirIfNeedScanned(dir);
            List<RemoteFile> nextDirs = ftpsFileStorage.listDirs(dir);
            // basePath = dirList.removeFirst()
            List<String> toAddDirs = nextDirs.stream().map(f -> PathUtils.standardPath(f.basePath() + "/" + f.name())).toList();
            dirList.addAll(toAddDirs);

            System.out.println(id + " List dir ==== " + dir + " dir size: " + toAddDirs.size());
            // 处理该目录中的文件
            // 总共执行：retryThreshold + 1 次
            SendWithRetryTask task = new SendWithRetryTask(id, dir, 6, 10);
            scheduledExecutorService.execute(task);
        }
    }

    static String inputPath = "/";

    static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

    public static class SendWithRetryTask implements Runnable {
        final String id;
        final String dir;
        final int retryThreshold; // 重试次数
        // volatile 保证其他线程执行时可见（下次执行可能不是当前线程）
        volatile AtomicInteger retryCount; // curr次数
        final long interval;

        public SendWithRetryTask(String id, String dir, int retryThreshold, long interval) {
            this.id = id;
            this.dir = dir;
            this.retryThreshold = retryThreshold;
            this.retryCount = new AtomicInteger(0);
            this.interval = interval;
        }

        @Override
        public void run() {
            List<RemoteFile> remoteFiles = ftpsFileStorage.listFiles(dir);
            System.out.println(id + " List file ==== " + dir + " file size: " + remoteFiles.size());
            if (remoteFiles.isEmpty()) {
                // 重试
//                retryCount.incrementAndGet();
//                mayRetry(this);
            } else {
                // 下载备份
                for (RemoteFile remoteFile : remoteFiles) {
                    File file = new File(PathUtils.standardPath("E:/tmp/ftps/"
                        + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH"))
                        + "/" + PathUtils.getRelativePath(inputPath, dir) + "/" + remoteFile.name()));
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
//                    ftpsFileStorage.download(remoteFile.basePath() + "/" + remoteFile.name(), file);

                    // 发送
                }

            }
        }
    }

    public static void mayRetry(SendWithRetryTask task) {
        if (task.retryCount.get() > task.retryThreshold) {
            System.out.println(task.id + " ==== " + task.dir + " 重试结束");
            return;
        }
        if (!isRequestRetry(task.dir)) {
            System.out.println(task.id + " ==== " + task.dir + " 无需重试");
            return;
        }
        System.out.println(task.id + " ==== " + task.dir + " 第几次重试：" + task.retryCount);
        scheduledExecutorService.schedule(task, task.interval, TimeUnit.SECONDS);
    }

    public static boolean isRequestRetry(String dir) {
        return inputPath.equals(dir);
    }
}
