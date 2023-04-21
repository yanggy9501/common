package com.freeing.common.web.configuration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author yanggy
 */
public class I18nLocaleResolver implements LocaleResolver {

    private HttpServletRequest request;

    public I18nLocaleResolver(HttpServletRequest request) {
        this.request = request;
    }

    public Locale getLocal() {
        return resolveLocale(request);
    }

    /**
     * 从 HttpServletRequest 中获取 Locale
     * 
     * @param httpServletRequest httpServletRequest
     * @return Locale
     */
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String language = httpServletRequest.getParameter("lang");
        Locale locale = Locale.getDefault();
        if (StringUtils.isNotEmpty(language)) {
            String[] split = language.split("_");
            locale = split.length > 1 ? new Locale(split[0], split[1]) : new Locale(split[0]);

        }
        return locale;
    }

    /**
     * 用于实现 Local 的切换，如用户想要切换中英文展示样式
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param locale
     */
    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
