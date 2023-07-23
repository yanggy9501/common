package com.freeing.common.log.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yanggy
 */
@ConfigurationProperties(prefix = "app.log")
public class OperationLogProperties {

    /**
     * 日志记录需要忽略的属性字段，多个则以逗号隔开，如：field1,field2
     */
    private String excludeProperties;

    public String getExcludeProperties() {
        return excludeProperties;
    }

    public void setExcludeProperties(String excludeProperties) {
        this.excludeProperties = excludeProperties;
    }
}
