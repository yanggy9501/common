package com.freeing.common.web.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author yanggy
 */
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        beanFactory = applicationContext;
    }

    public static ApplicationContext getSpringFactory() {
        return beanFactory;
    }

    /**
     * 获取 Bean
     *
     * @param beanName bean name
     * @param beanClass Bean 的类型，如果beanName 对应的 Bean 不是该类型，就会尝试转换为该类型
     * @return Bean
     */
    public static  <T> T getBean(String beanName, Class<T> beanClass) {
        return beanFactory.getBean(beanName, beanClass);
    }

    public static <T> T getBean(Class<T> beanClass) {
        return beanFactory.getBean(beanClass);
    }

    public static Object getBean(String beanName) {
        return beanFactory.getBean(beanName);
    }
}
