package com.freeing.common.xfile;


import java.util.Date;

public class FileAttrs {
    /**
     * 文件、目录名
     */
    private String filename;

    /**
     * 父路径，标准格式
     */
    private String parentPath;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 文件大小，单位：byte
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
        return "FtpFileAttrs{" +
            "filename='" + filename + '\'' +
            ", parentPath='" + parentPath + '\'' +
            ", extension='" + extension + '\'' +
            ", size=" + size +
            ", lastUpdateTime=" + lastUpdateTime +
            '}';
    }
}
