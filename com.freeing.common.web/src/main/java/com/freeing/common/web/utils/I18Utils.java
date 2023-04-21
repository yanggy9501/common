package com.freeing.common.web.utils;

import com.freeing.common.web.configuration.I18nLocaleResolver;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * @author yanggy
 */
public class I18Utils {
    private static final Logger logger = LoggerFactory.getLogger(I18Utils.class);

    private final I18nLocaleResolver resolver;

    private static I18nLocaleResolver customLocaleResolver;

    private final ResourceBundleMessageSource messageSource;
    private static ResourceBundleMessageSource customMessageSource;

    public I18Utils(I18nLocaleResolver localeResolver, ResourceBundleMessageSource messageSource) {
        this.resolver = localeResolver;
        this.messageSource = messageSource;
    }

    @PostConstruct
    public void init() {
        I18Utils.customLocaleResolver = resolver;
        I18Utils.customMessageSource = messageSource;
    }

    /**
     * 获取 国际化后内容信息
     *
     * @param code 国际化key
     * @return 国际化后内容信息
     */
    public static String getMessage(String code) {
        Locale locale = customLocaleResolver.getLocal();
        return getMessage(code, null, code, locale);
    }

    /**
     * 获取指定语言中的国际化信息，如果没有则走英文
     *
     * @param code 国际化 key
     * @param lang 语言参数，如：zh_CN
     * @return 国际化后内容信息
     */
    public static String getMessage(String code, String lang) {
        Locale locale;
        if (StringUtils.isEmpty(lang)) {
            locale = Locale.US;
        } else {
            try {
                String[] split = lang.split("_");
                locale = new Locale(split[0], split.length == 2 ? split[1] : "");
            } catch (Exception e) {
                locale = Locale.US;
            }
        }
        return getMessage(code, null, code, locale);
    }

    /**
     * 获取指定语言中的国际化信息，如果没有则走英文
     *
     * @param code 国际化 key
     * @param locale 语言
     * @return 国际化后内容信息
     */
    public static String getMessage(String code, Locale locale) {
        return getMessage(code, null, code, locale);
    }

    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        String content;
        try {
            content = customMessageSource.getMessage(code, args, locale);
        } catch (Exception e) {
            logger.error("国际化参数获取失败", e);
            content = defaultMessage;
        }
        return content;
    }
}
