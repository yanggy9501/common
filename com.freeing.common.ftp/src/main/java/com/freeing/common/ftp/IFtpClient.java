package com.freeing.common.ftp;

import com.freeing.common.ftp.attr.Attrs;
import com.freeing.common.ftp.enums.FileType;

import java.io.InputStream;
import java.util.List;

/**
 * FTP 客服端接口
 *
 * @author yanggy
 */
public interface IFtpClient {
    /**
     * 获取FTP/SFTP所处根目录
     *
     * @return 根目录
     */
    String getRootPath();

    /**
     * 获取文件类型
     *
     * @param path 文件或目录路径
     */
    FileType getType(String path);

    /**
     * 根据上级目录列出下级目录的名称列表
     *
     * @param dirPath  目录名称
     * @return dirPath 目录名称列表
     */
    List<Attrs> list(String dirPath);

    /**
     * 断开连接
     */
    void disconnect();

    /**
     * 切换目录
     *
     * @param dirPath 目录
     */
    void changeDirectory(String dirPath);

    /**
     * 读取文件
     *
     * @param filePath 文件路径
     * @return 二进制文件
     */
    byte[] readFile(String filePath);

    /**
     * 获取文件流
     *
     * @param filePath 文件路径
     * @return 流需要调用者自行关闭
     */
    InputStream getFile(final String filePath);

    /**
     * 上传文件
     *
     * @param uploadIn  文件输入流
     * @param remoteDir 上传到FTP服务的所在目录
     * @param filename  存储的文件名
     */
    void upload(InputStream uploadIn, String remoteDir, String filename);

    /**
     * 删除远程 FTP文件
     * PS: 谨慎使用，使用时做好安全控制避免恶意删除
     *
     * @param filePath 远程FTP文件路径
     */
    void deleteFile(String filePath);
}
