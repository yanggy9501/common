package com.freeing.common.log.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yanggy
 */
@ConfigurationProperties(prefix = "app.log.field")
@ConditionalOnProperty(name = "app.log.enabled", havingValue = "false")
public class OperationLogProperties {

    private String excludeProperties;

    public String getExcludeProperties() {
        return excludeProperties;
    }

    public void setExcludeProperties(String excludeProperties) {
        this.excludeProperties = excludeProperties;
    }
}
