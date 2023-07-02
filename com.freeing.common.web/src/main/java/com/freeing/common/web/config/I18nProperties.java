package com.freeing.common.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author yanggy
 */
@ConfigurationProperties(prefix = "spring.messages")
public class I18nProperties {

    private List<String> basename;

    public List<String> getBasename() {
        return basename;
    }

    public void setBasename(List<String> basename) {
        this.basename = basename;
    }
}
