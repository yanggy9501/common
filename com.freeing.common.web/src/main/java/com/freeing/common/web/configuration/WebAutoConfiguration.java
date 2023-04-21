package com.freeing.common.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yanggy
 */
@Configuration
@ComponentScan("com.freeing.common.web")
public class WebAutoConfiguration {

    @Bean
    public I18nLocaleResolver i18nLocaleResolver(HttpServletRequest request) {
        return new I18nLocaleResolver(request);
    }
}
