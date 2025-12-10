package com.freeing.common.ftp.factory;

import com.freeing.common.ftp.config.FileStorageProperties.FtpsConfig;
import com.freeing.common.ftp.exception.FtpException;
import com.freeing.common.ftp.ftp.Ftps;
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

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.nio.charset.Charset;

public class FtpsFileStorageClientFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(FtpsFileStorageClientFactory.class);
    private final String protocol;
    private final String host;
    private final Integer port;
    private final String user;
    private final String password;
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
            throw new FtpException("Get SFTP Client failed", e);
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
            // Explicit 模式（推荐）
            FTPSClient ftpsClient = new FTPSClient(factory.getProtocol(), factory.isImplicit());
            // 证书验证
            trustCertifications(ftpsClient);

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

            ftpsClient.setControlEncoding("UTF-8");
            return new Ftps(ftpsClient);
        }

        @Override
        public PooledObject<Ftps> wrap(Ftps p) {
            return new DefaultPooledObject<>(p);
        }

        @Override
        public boolean validateObject(PooledObject<Ftps> p) {
            try {
                p.getObject().cd(".");
                return true;
            } catch (FtpException e) {
                LOGGER.warn("Validate Ftps object failed", e);
                return false;
            }
        }

        @Override
        public void destroyObject(PooledObject<Ftps> p) {
            try {
                p.getObject().close();
            } catch (Exception e) {
                throw new FtpException("Destroy pooled object failed", e);
            }
        }
    }

    private static void trustCertifications(FTPSClient ftpsClient) {
        TrustManager[] trustManager = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
            }
        }};
        ftpsClient.setTrustManager(trustManager[0]);
    }
}
