package com.freeing.common.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanggy
 */
@Configuration
@ComponentScan("com.freeing.common.web")
public class WebAutoConfiguration {

    @Bean
    public I18nLocaleResolver i18nLocaleResolver() {
        return new I18nLocaleResolver();
    }
}
