package com.freeing.common.web;

import com.freeing.common.web.config.I18nLocaleResolver;
import com.freeing.common.web.config.I18nProperties;
import com.freeing.common.web.util.I18Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

/**
 * @author yanggy
 */
@Configuration
@ConditionalOnBean(value = {I18nProperties.class})
@EnableConfigurationProperties(I18nProperties.class)
public class WebAutoI18nConfiguration {

    @Autowired
    private I18nProperties i18nProperties;

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames(i18nProperties.getBasename().toArray(new String[0]));
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        return messageSource;
    }

    @Bean
    public I18nLocaleResolver i18nLocaleResolver() {
        return new I18nLocaleResolver();
    }

    @Bean
    public I18Utils i18Utils(I18nLocaleResolver localeResolver, ResourceBundleMessageSource messageSource) {
        return new I18Utils(localeResolver, messageSource);
    }
}
