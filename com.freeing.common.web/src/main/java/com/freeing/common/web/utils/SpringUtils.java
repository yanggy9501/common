package com.freeing.common.web.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author yanggy
 */
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext springFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        springFactory = applicationContext;
    }

    public static ApplicationContext getSpringFactory() {
        return springFactory;
    }
}
