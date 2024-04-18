package com.freeing.common.ftp.enums;

/**
 * 文件类型
 */
public enum FType {
    NOT_EXIST,// 不存在
    FILE, // 普通文件
    DIRECTORY, // 目录
    LINK, // 链接文件
    OTHER;// 其他类型，如设备
}
