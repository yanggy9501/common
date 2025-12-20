package com.freeing.common.xfile.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FileStorageProperties {
    /**
     * FTPS
     */
    private List<? extends FtpsConfig> ftps = new ArrayList<>();

    /**
     * SFTP
     */
    private List<? extends SftpConfig> sftp = new ArrayList<>();

    public static class SftpConfig {
        /**
         * 主机
         */
        private String host;

        /**
         * 端口，默认22
         */
        private int port = 22;

        /**
         * 用户名
         */
        private String user;

        /**
         * 密码
         */
        private String password;

        /**
         * 私钥路径
         */
        private String privateKeyPath;

        /**
         * 编码，默认UTF-8
         */
        private Charset charset = StandardCharsets.UTF_8;

        /**
         * 连接超时时长，单位毫秒，默认10秒
         */
        private int connectionTimeout = 10 * 1000;

        /**
         * 基础路径
         */
        private String basePath = "";


        /**
         * Client 对象池配置
         */
        private CommonClientPoolConfig pool = new CommonClientPoolConfig();

        /**
         * 其它自定义配置
         */
        private Map<String, Object> attr = new LinkedHashMap<>();

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPrivateKeyPath() {
            return privateKeyPath;
        }

        public void setPrivateKeyPath(String privateKeyPath) {
            this.privateKeyPath = privateKeyPath;
        }

        public Charset getCharset() {
            return charset;
        }

        public void setCharset(Charset charset) {
            this.charset = charset;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public String getBasePath() {
            return basePath;
        }

        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }

        public CommonClientPoolConfig getPool() {
            return pool;
        }

        public void setPool(CommonClientPoolConfig pool) {
            this.pool = pool;
        }

        public Map<String, Object> getAttr() {
            return attr;
        }

        public void setAttr(Map<String, Object> attr) {
            this.attr = attr;
        }
    }

    public static class FtpsConfig {
        private String protocol = "TLS";

        /**
         * 主机
         */
        private String host;

        /**
         * 端口，默认21
         */
        private int port = 21;

        /**
         * 用户名
         */
        private String username;

        /**
         * 密码
         */
        private String password;

        /**
         * 编码，默认UTF-8
         */
        private Charset charset = StandardCharsets.UTF_8;

        /**
         * 连接超时时长，单位毫秒，默认10秒
         */
        private int connectionTimeout = 10 * 1000;

        /**
         * 基础路径
         */
        private String basePath = "";

        /**
         * 是否使用 Implicit 隐式模式
         */
        private boolean isImplicit = false;

        /**
         * Client 对象池配置
         */
        private CommonClientPoolConfig pool = new CommonClientPoolConfig();

        /**
         * 其它自定义配置
         */
        private Map<String, Object> attr = new LinkedHashMap<>();

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Charset getCharset() {
            return charset;
        }

        public void setCharset(Charset charset) {
            this.charset = charset;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public String getBasePath() {
            return basePath;
        }

        public void setBasePath(String basePath) {
            this.basePath = basePath;
        }

        public CommonClientPoolConfig getPool() {
            return pool;
        }

        public void setPool(CommonClientPoolConfig pool) {
            this.pool = pool;
        }

        public Map<String, Object> getAttr() {
            return attr;
        }

        public void setAttr(Map<String, Object> attr) {
            this.attr = attr;
        }

        public boolean isImplicit() {
            return isImplicit;
        }

        public void setImplicit(boolean implicit) {
            isImplicit = implicit;
        }
    }

    public static class CommonClientPoolConfig {
        /**
         * 取出对象前进行校验，默认开启
         */
        private Boolean testOnBorrow = true;

        /**
         * 空闲检测，默认开启
         */
        private Boolean testWhileIdle = true;

        /**
         * 最大总数量，超过此数量会进行阻塞等待，默认 16
         */
        private Integer maxTotal = 16;

        /**
         * 最大空闲数量，默认 4
         */
        private Integer maxIdle = 4;

        /**
         * 最小空闲数量，默认 1
         */
        private Integer minIdle = 1;

        /**
         * 空闲对象逐出（销毁）运行间隔时间，默认 30 秒
         */
        private Duration timeBetweenEvictionRuns = Duration.ofSeconds(30);

        /**
         * 对象空闲超过此时间将逐出（销毁），为负数则关闭此功能，默认 -1
         */
        private Duration minEvictableIdleDuration = Duration.ofMillis(-1);

        /**
         * 对象空闲超过此时间且当前对象池的空闲对象数大于最小空闲数量，将逐出（销毁），为负数则关闭此功能，默认 30 分钟
         */
        private Duration softMinEvictableIdleDuration = Duration.ofMillis(30);

        public <T> GenericObjectPoolConfig<T> toGenericObjectPoolConfig() {
            GenericObjectPoolConfig<T> config = new GenericObjectPoolConfig<>();
            config.setTestOnBorrow(testOnBorrow);
            config.setTestWhileIdle(testWhileIdle);
            config.setMaxTotal(maxTotal);
            config.setMinIdle(minIdle);
            config.setMaxIdle(maxIdle);
            config.setTimeBetweenEvictionRuns(timeBetweenEvictionRuns);
            config.setMinEvictableIdleDuration(minEvictableIdleDuration);
            config.setSoftMinEvictableIdleDuration(softMinEvictableIdleDuration);
            return config;
        }

        public Boolean getTestOnBorrow() {
            return testOnBorrow;
        }

        public void setTestOnBorrow(Boolean testOnBorrow) {
            this.testOnBorrow = testOnBorrow;
        }

        public Boolean getTestWhileIdle() {
            return testWhileIdle;
        }

        public void setTestWhileIdle(Boolean testWhileIdle) {
            this.testWhileIdle = testWhileIdle;
        }

        public Integer getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(Integer maxTotal) {
            this.maxTotal = maxTotal;
        }

        public Integer getMaxIdle() {
            return maxIdle;
        }

        public void setMaxIdle(Integer maxIdle) {
            this.maxIdle = maxIdle;
        }

        public Integer getMinIdle() {
            return minIdle;
        }

        public void setMinIdle(Integer minIdle) {
            this.minIdle = minIdle;
        }

        public Duration getTimeBetweenEvictionRuns() {
            return timeBetweenEvictionRuns;
        }

        public void setTimeBetweenEvictionRuns(Duration timeBetweenEvictionRuns) {
            this.timeBetweenEvictionRuns = timeBetweenEvictionRuns;
        }

        public Duration getMinEvictableIdleDuration() {
            return minEvictableIdleDuration;
        }

        public void setMinEvictableIdleDuration(Duration minEvictableIdleDuration) {
            this.minEvictableIdleDuration = minEvictableIdleDuration;
        }

        public Duration getSoftMinEvictableIdleDuration() {
            return softMinEvictableIdleDuration;
        }

        public void setSoftMinEvictableIdleDuration(Duration softMinEvictableIdleDuration) {
            this.softMinEvictableIdleDuration = softMinEvictableIdleDuration;
        }
    }
}
