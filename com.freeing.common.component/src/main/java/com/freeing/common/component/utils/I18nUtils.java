package com.freeing.common.component.utils;

import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

/**
 * @author yanggy
 */
public class I18nUtils {
    public static Properties I18N_ZH_CN_PROPERTIES;
    public static Properties I18N_EN_US_PROPERTIES;


    public static String get(String key, Locale locale) {
        if (Objects.equals(Locale.SIMPLIFIED_CHINESE, locale) || Objects.equals("zh", locale.getLanguage())) {
            return I18N_ZH_CN_PROPERTIES.get(key).toString();
        } else if (Objects.equals(Locale.ENGLISH, locale) || Objects.equals("en", locale.getLanguage())) {
            return I18N_EN_US_PROPERTIES.get(key).toString();
        } else {
            return "";
        }
    }
}
