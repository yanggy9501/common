package com.freeing.common.ftp;

import com.freeing.common.ftp.enums.FileType;

import java.util.Date;

public class FtpAttrs {
    /**
     * 文件、目录名
     */
    private String filename;

    /**
     * 父路径，标准格式
     */
    private String parentPath;

    /**
     * 类型：普通文件、目录，超链接等等
     */
    private FileType type;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 文件大小，单位：byte，注意目录的大小不是其目录中所有文件大小之后，目录本身也是文件也是有大小的
     */
    private Long size;

    /**
     * 最后更新时间
     */
    private Date lastUpdateTime;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getParentPath() {
        return parentPath;
    }

    public void setParentPath(String parentPath) {
        this.parentPath = parentPath;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "FtpAttrs{" +
            "filename='" + filename + '\'' +
            ", parentPath='" + parentPath + '\'' +
            ", type=" + type +
            ", extension='" + extension + '\'' +
            ", size=" + size +
            ", lastUpdateTime=" + lastUpdateTime +
            '}';
    }
}
