package com.freeing.common.ftp;

/**
 * FTP client 配置属性
 */
public class FtpProperty {
    /**
     * 协议：sftp|ftp
     */
    private String protocol;

    /**
     * ftp 主机
     */
    private String host;

    /**
     * ftp 端口
     */
    private int port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 凭证：密码或密钥文件
     * 格式=协议@凭证
     * 密码 pwd@密码
     * 密钥 prk@密钥文件路径
     */
    private String certificate;

    /**
     * 会话超时时间（单位：毫秒）
     */
    private int timeout;

    private FtpProperty() {

    }

    private FtpProperty(String protocol, String host, int port, String username, String certificate, int timeout) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.username = username;
        this.certificate = certificate;
        this.timeout = timeout;
    }

    public static Builder builder(String protocol, String host, int port, String username) {
        return new Builder(protocol, host, port, username);
    }

    public static class Builder {
        private final String protocol;

        private final String host;

        private final int port;

        private final String username;

        private String certificate;

        private int timeout;

        public Builder(String protocol, String host, int port, String username) {
            this.protocol = protocol;
            this.host = host;
            this.port = port;
            this.username = username;
        }

        public Builder password(String password) {
            if (password != null && !password.isEmpty()) {
                certificate = "pwd@" + password;
            }
            return this;
        }

        public Builder privateKeyFile(String privateKeyFile) {
            if (privateKeyFile != null && !privateKeyFile.isEmpty()) {
                certificate = "prk@" + privateKeyFile;
            }
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public FtpProperty build() {
            return new FtpProperty(protocol, host, port, username, certificate, timeout);
        }
    }

    public boolean isPassword() {
        return certificate != null && certificate.startsWith("pwd");
    }

    public boolean isPrivateKey() {
        return certificate != null && certificate.startsWith("prk");
    }

    public String getProtocol() {
        return protocol;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getCertificate() {
        return certificate.substring(certificate.indexOf("@") + 1);
    }

    public int getTimeout() {
        return timeout;
    }

}
