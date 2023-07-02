package com.freeing.common.web.config;

import com.freeing.common.web.util.ServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author yanggy
 */
public class I18nLocaleResolver implements LocaleResolver {

    public String LANG_KEY = "i18n_lang";

    public Locale getLocal() {
        return resolveLocale(ServletUtils.getRequest());
    }

    /**
     * 从 HttpServletRequest 中获取 Locale
     * 
     * @param httpServletRequest httpServletRequest
     * @return Locale
     */
    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String lang = null;
        if (httpServletRequest.getAttribute(LANG_KEY) != null) {
            lang = (String) httpServletRequest.getAttribute(LANG_KEY);
        } else {
            lang = httpServletRequest.getParameter(LANG_KEY);
        }
        Locale locale = Locale.getDefault();
        if (StringUtils.isNotEmpty(lang)) {
            String[] split = lang.split("_");
            locale = split.length > 1 ? new Locale(split[0], split[1]) : new Locale(split[0]);

        }
        return locale;
    }

    /**
     * 用于实现 Local 的切换，如用户想要切换中英文展示样式
     * 需要配置拦截器去设置国际化，调用 I18nLocaleResolver#setLocale
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param locale
     */
    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {
        if (locale != null) {
            String language = locale.getLanguage();
            String country = locale.getCountry();
            String localeStr = country == null || country.isEmpty() ? language : language + "_" + country;
            httpServletRequest.setAttribute(LANG_KEY, localeStr);
        }
    }
}
