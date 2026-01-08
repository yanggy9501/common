package com.freeing.common.xfile.ftp.ext;

import com.freeing.common.xfile.exception.FtpException;
import org.apache.commons.net.ftp.FTPSClient;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jsse.BCExtendedSSLSession;
import org.bouncycastle.jsse.BCSSLSocket;
import org.bouncycastle.jsse.provider.BouncyCastleJsseProvider;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.*;

/**
 * FTPSClient 的 BouncyCastle 增强版，支持 TLS 会话重用（针对 FileZilla 等服务器），
 * 这个类通过 BouncyCastle 绕过了 JDK 原生 TLS 的会话重用限制
 * 特别适合 FileZilla 等要求严格重用的 FTPS 服务器。
 * 解决：425 Unable to build data connection: TLS session of data connection not resumed.
 * 解决：522 SSL connection failed; session reuse required.
 */
public class FTPSClientWithResumeBC extends FTPSClient {
    static {
        // 注册提供者（JCE 和 JSSE）
        // 允许 Java 使用 BouncyCastle 的加密实现
        Security.addProvider(new BouncyCastleProvider());
        // 用于后续创建支持会话重用的 TLS 上下文。
        Security.addProvider(new BouncyCastleJsseProvider());
    }

    public FTPSClientWithResumeBC() {
        super(false, createSslContext());
    }

    private static SSLContext createSslContext() {
        try {
            // 使用 "TLS"（支持 TLS 1.2/1.3，根据服务器协商）
            // 使用 BouncyCastle JSSE 提供者（"BCJSSE"）获取一个名为 "TLS" 的 SSL 上下文实例。或指定 "TLSv1.2" 以兼容旧服务器
            // 核心：它使用 BouncyCastle 的 TLS 实现，支持会话重用。
            SSLContext sslContext = SSLContext.getInstance("TLS", "BCJSSE");
            // 默认信任管理器（生产环境可自定义 KeyManager/TrustManager 以支持客户端证书）
            // 自定义 TrustManager：信任所有证书（开发用）
//            TrustManager[] trustManagers = new TrustManager[] {
//                new X509TrustManager() {
//                    @Override
//                    public void checkClientTrusted(X509Certificate[] chain, String authType) {}
//
//                    @Override
//                    public void checkServerTrusted(X509Certificate[] chain, String authType) {}
//
//                    @Override
//                    public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
//                }
//            };
            // null：KeyManager 数组为空（无需客户端密钥）。
            // trustManagers：上述自定义信任管理器数组。
            // new SecureRandom()：随机数生成器，用于加密操作的安全随机源。
//            sslContext.init(null, trustManagers, new SecureRandom());
            sslContext.init(null, null, new SecureRandom());
            return sslContext;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | KeyManagementException e) {
            throw new FtpException("无法创建 SSL 上下文: " + e.getMessage(), e);
        }
    }

    @Override
    protected void _connectAction_() throws IOException {
        // 先调用父类的实现，完成标准连接（包括 STARTTLS 握手）。
        super._connectAction_();
        // 0 表示不限制缓冲区大小（允许全 TLS 保护）
        execPBSZ(0);
        // 参数 "P" 表示私有保护（Private，全 TLS 加密数据通道）
        execPROT("P");
    }

    // 通过 BCExtendedSSLSession 和 setBCSessionToResume 实现（BouncyCastle 专有 API）
    @Override
    protected void _prepareDataSocket_(java.net.Socket dataSocket) throws IOException {
        if (_socket_ instanceof BCSSLSocket sslSocket) {
            BCExtendedSSLSession bcSession = sslSocket.getBCSession();
            if (bcSession != null && bcSession.isValid() && dataSocket instanceof BCSSLSocket dataSslSocket) {
                // 设置数据连接重用控制连接的会话
                dataSslSocket.setBCSessionToResume(bcSession);
                // 设置 SNI（如果服务器要求）
                dataSslSocket.setHost(bcSession.getPeerHost());
            }
        }
        super._prepareDataSocket_(dataSocket);
    }
}