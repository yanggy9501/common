package com.freeing.common.ftp;


public abstract class AbstractFtpClient<T> implements IFtpClient {
    /**
     * 客户端（FTP/FTPS/SFTP）
     */
    protected T client;

    /**
     * 用户根目录
     */
    protected String rootPath;

    /**
     * 标准化路径
     *
     * @param aPath 文件路径
     */
    public static String standardPath(String aPath) {
        String path = aPath == null ? "" : aPath.trim();
        if (path.isEmpty()) {
            return "";
        }

        if (path.equals("/")) {
            return path;
        }

        // 反斜杠"\\"转正"/"
        path = path.replaceAll("\\\\", "/");
        // 双正斜杠"//"去重 "/"
        while (path.contains("//")) {
            path = path.replaceAll("//", "/");
        }

        // 去掉结尾 "/"
        if (path.endsWith("/")) {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }

    /**
     * 创建连接
     *
     * @param host           FTP服务器IP
     * @param port           FTP服务器端口
     * @param username       FTP连接用户名
     * @param privateKeyFile FTP密钥文件
     */
    protected abstract void connectByPrk(String host, int port, String username, String privateKeyFile);

    /**
     * 创建连接
     *
     * @param host     FTP服务器IP
     * @param port     FTP服务器端口
     * @param username FTP连接用户名
     * @param password 密码
     */
    protected abstract void connectByPwd(String host, int port, String username, String password);

    /**
     * 切换目录
     *
     * @param dirPath 目录
     */
    protected abstract void changeDirectory(String dirPath);

    /**
     * 创建目录
     * 如果父级目录不存在则创建父级目录
     *
     * @param dirPath 目录
     */
    protected abstract void makeDirectory(String dirPath);

    @Override
    public void close() throws Exception {
        disconnect();
    }

    protected T getClient() {
        return client;
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }
}
