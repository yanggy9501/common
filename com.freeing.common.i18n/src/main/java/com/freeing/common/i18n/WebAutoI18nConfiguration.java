package com.freeing.common.i18n;

import com.freeing.common.i18n.config.I18nLocaleResolver;
import com.freeing.common.i18n.config.I18nProperties;
import com.freeing.common.i18n.util.I18nUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

/**
 * @author yanggy
 */
@Configuration
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
    public I18nUtils i18nUtils(I18nLocaleResolver localeResolver, ResourceBundleMessageSource messageSource) {
        return new I18nUtils(localeResolver, messageSource);
    }
}
