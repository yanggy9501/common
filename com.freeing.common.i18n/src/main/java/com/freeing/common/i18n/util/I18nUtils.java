package com.freeing.common.i18n.util;

import com.freeing.common.i18n.config.I18nLocaleResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * @author yanggy
 */
public class I18nUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(I18nUtils.class);

    private final I18nLocaleResolver resolver;

    private static I18nLocaleResolver customLocaleResolver;

    private final ResourceBundleMessageSource messageSource;

    private static ResourceBundleMessageSource customMessageSource;

    public I18nUtils(I18nLocaleResolver localeResolver, ResourceBundleMessageSource messageSource) {
        this.resolver = localeResolver;
        this.messageSource = messageSource;
    }

    @PostConstruct
    public void init() {
        LOGGER.info("Load i18n files [{}]", messageSource.getBasenameSet());
        I18nUtils.customLocaleResolver = resolver;
        I18nUtils.customMessageSource = messageSource;
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
        } catch (NullPointerException e) {
            throw new NullPointerException("May not configure yaml 'spring.messages'. Look I18nProperties.class");
        } catch (Exception e) {
            LOGGER.warn("Fail to get i18n code's message, code = {}", code, e);
            content = defaultMessage;
        }
        return content;
    }
}
