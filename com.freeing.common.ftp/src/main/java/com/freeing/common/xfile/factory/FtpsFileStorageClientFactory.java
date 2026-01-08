package com.freeing.common.xfile.factory;

import com.freeing.common.xfile.config.FileStorageProperties.FtpsConfig;
import com.freeing.common.xfile.exception.FtpException;
import com.freeing.common.xfile.ftp.Ftps;
import com.freeing.common.xfile.ftp.ext.FTPSClientWithResumeBC;
import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;

/**
 * create → wrap
 *    ↓
 * idle → borrow → activateObject -> setTestOnBorrow(true) ? -> validateObject()
 *    ↓
 * 使用中
 *    ↓
 * return → passivateObject → idle
 *    ↓
 * validate / destroy
 */
public class FtpsFileStorageClientFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(FtpsFileStorageClientFactory.class);
    private final String protocol;
    private final String host;
    private final Integer port;
    private final String user;
    private final String password;
    private final String basePath;
    private final Charset charset;
    private final Integer connectionTimeout;
    private final boolean isImplicit;
    private final GenericObjectPoolConfig<Ftps> poolConfig;
    private volatile GenericObjectPool<Ftps> pool;

    public FtpsFileStorageClientFactory(FtpsConfig config) {
        this.protocol = config.getProtocol();
        this.host = config.getHost();
        this.port = config.getPort();
        this.user = config.getUsername();
        this.password = config.getPassword();
        this.charset = config.getCharset();
        this.connectionTimeout = config.getConnectionTimeout();
        this.isImplicit = config.isImplicit();
        this.poolConfig = config.getPool().toGenericObjectPoolConfig();
        this.basePath = config.getBasePath();
    }

    public Ftps getClient() {
        try {
            if (pool == null) {
                synchronized (this) {
                    if (pool == null) {
                        pool = new GenericObjectPool<>(new FtpsPooledObjectFactory(this), poolConfig);
                    }
                }
            }
            return pool.borrowObject();
        } catch (Exception e) {
            throw new FtpException("Get FTPS Client failed", e);
        }
    }

    public void returnClient(Ftps client) {
        try {
            pool.returnObject(client);
        } catch (Exception e) {
            throw new FtpException("Return SFTP Client failed", e);
        }
    }

    public void close() {
        if (pool != null) {
            pool.close();
            pool = null;
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public Integer getPort() {
        return port;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getBasePath() {
        return basePath;
    }

    public Charset getCharset() {
        return charset;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public boolean isImplicit() {
        return isImplicit;
    }

    public GenericObjectPoolConfig<Ftps> getPoolConfig() {
        return poolConfig;
    }

    public GenericObjectPool<Ftps> getPool() {
        return pool;
    }

    public static class FtpsPooledObjectFactory extends BasePooledObjectFactory<Ftps> {
        private final FtpsFileStorageClientFactory factory;

        public FtpsPooledObjectFactory(FtpsFileStorageClientFactory factory) {
            this.factory = factory;
        }

        @Override
        public Ftps create() throws Exception {
            // 可选：禁用 EMS 以提升兼容性（如果服务器不支持）
            System.setProperty("jdk.tls.useExtendedMasterSecret", "false");
            FTPSClient ftpsClient = new FTPSClientWithResumeBC();
            // 一定要在 connect() 之前设置，解决中文路径
            ftpsClient.setControlEncoding("UTF-8");

            ftpsClient.connect(factory.getHost(), factory.getPort());
            int replyCode = ftpsClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new FtpException("FTPS connection failed");
            }

            if (!ftpsClient.login(factory.getUser(), factory.getPassword())) {
                throw new FtpException("FTPS login failed");
            }
            // 0 使用默认缓存
            ftpsClient.execPBSZ(0);
            // 加密数据通道: Private（数据完全加密，加密文件内容）, 使用 PROT P 才是“真正的 FTPS”。
            ftpsClient.execPROT("P");
            // 被动模式(客户端主动去连接服务器的被动端口)
            ftpsClient.enterLocalPassiveMode();
            // 二进制模式(适用文件下载和上传)
            ftpsClient.setFileType(FTP.BINARY_FILE_TYPE);

            ftpsClient.setDataTimeout(Duration.ofSeconds(300));
            ftpsClient.setConnectTimeout(factory.getConnectionTimeout());
            ftpsClient.setDefaultTimeout(factory.getConnectionTimeout());
//            ftpsClient.addProtocolCommandListener(new ProtocolCommandLoggingListener());
            return new Ftps(ftpsClient);
        }

        @Override
        public PooledObject<Ftps> wrap(Ftps p) {
            return new DefaultPooledObject<>(p);
        }

        @Override
        public void activateObject(PooledObject<Ftps> p) throws Exception {
            FTPSClient client = p.getObject().client();
            // 强制二进制模式
            client.setFileType(FTP.BINARY_FILE_TYPE);
            // 强制被动模式
            client.enterLocalPassiveMode();
        }

        @Override
        public void passivateObject(PooledObject<Ftps> p) throws Exception {
            FTPSClient client = p.getObject().client();
            // 统一工作目录
            client.changeWorkingDirectory(factory.getBasePath());
        }

        @Override
        public boolean validateObject(PooledObject<Ftps> p) {
            FTPSClient ftpsClient = p.getObject().client();
            try {
                return ftpsClient.isConnected() && ftpsClient.sendNoOp();
            } catch (IOException e) {
                LOGGER.warn("Validate Ftps object failed", e);
                return false;
            }
        }

        @Override
        public void destroyObject(PooledObject<Ftps> p) {
            FTPSClient ftpsClient = p.getObject().client();
            try {
                if (ftpsClient.isConnected()) {
                    try {
                        ftpsClient.logout();
                    } catch (Exception ignore) {

                    }
                    ftpsClient.disconnect();
                }
            } catch (Exception e) {
                LOGGER.warn("Error destroying FTPSClient", e);
            }
        }
    }

    public static class ProtocolCommandLoggingListener implements ProtocolCommandListener {
        @Override
        public void protocolCommandSent(ProtocolCommandEvent protocolCommandEvent) {
            LOGGER.info("S {}", protocolCommandEvent.getMessage());
        }

        @Override
        public void protocolReplyReceived(ProtocolCommandEvent protocolCommandEvent) {
            LOGGER.info("R [{}] {}", + protocolCommandEvent.getReplyCode(), protocolCommandEvent.getMessage());
        }
    }
}
