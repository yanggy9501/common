package com.freeing.common.i18n.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanExpressionException;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * spring:
 *   # 国际化
 *   messages:
 *     basename: i18n/message
 *     或者
 *     basename
 *       - i18n/message
 *       - i18n/other
 *
 * @author yanggy
 */
@ConfigurationProperties(prefix = "spring.messages")
public class I18nProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(I18nProperties.class);

    private List<String> basename;

    @PostConstruct
    public void checkI18nConfig() {
        if (basename == null || basename.isEmpty()) {
            throw new BeanExpressionException("Do not config i18n value ${spring.messages.basename}");
        }
        LOGGER.info("Config i18n base file name {}", basename);
    }

    public List<String> getBasename() {
        return basename;
    }

    public void setBasename(List<String> basename) {
        this.basename = basename;
    }
}
