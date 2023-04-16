package com.freeing.common.xss;

import com.freeing.common.xss.converter.XssStringJsonDeserializer;
import com.freeing.common.xss.filter.XssFilter;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * xss 自动配置类
 *
 * @author yanggy
 */
public class XssAutoConfiguration {

    /**
     * 配置跨站攻击 反序列化处理器
     *
     * @return Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer myJackson2ObjectMapperBuilderCustomizer() {
        return builder -> builder.deserializerByType(String.class, new XssStringJsonDeserializer());
    }

    /**
     * 配置跨站攻击过滤器
     *
     * @return Filter
     */
    @Bean
    public FilterRegistrationBean<XssFilter> filterRegistrationBean() {
        FilterRegistrationBean<XssFilter> filterRegistration = new FilterRegistrationBean<>(new XssFilter());
        // 添加拦截的路径
        filterRegistration.addUrlPatterns("/*");
        filterRegistration.setOrder(1);
        // 设置初始化参数，XssFilter中init方法的FilterConfig对象能够取到
        Map<String, String> initParameters = new HashMap<>(2);
        String excludes = new StringJoiner(",")
            .add("/favicon.ico")
            .add("/doc.html")
            .add("/swagger-ui.html")
            .add("/csrf")
            .add("/webjars/*")
            .add("/v2/*")
            .add("/swagger-resources/*")
            .add("/resources/*")
            .add("/static/*")
            .add("/public/*")
            .add("/classpath:*")
            .add("/actuator/*")
            .toString();
        initParameters.put("excludes", excludes);
        initParameters.put("isIncludeRichText", "true");
        filterRegistration.setInitParameters(initParameters);
        return filterRegistration;
    }
}
