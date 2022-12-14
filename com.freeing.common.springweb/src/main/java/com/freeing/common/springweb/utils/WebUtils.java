package com.freeing.common.springweb.utils;


import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * web utils
 *
 * @author yanggy
 */
public class WebUtils {


    /**
     * 从parameterMap获取目标值，如果找不到，将返回默认值。
     *
     * @param req  {@link HttpServletRequest}
     * @param key  key
     * @param defaultValue default value
     * @return value
     */
    public static String optional(final HttpServletRequest req, final String key, final String defaultValue) {
        if (!req.getParameterMap().containsKey(key) || req.getParameterMap().get(key)[0] == null) {
            return defaultValue;
        }
        String value = req.getParameter(key);
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        String encoding = req.getParameter("encoding");
        return resolveValue(value, encoding);
    }

    /**
     * 从parameterMap获取目标值，如果找不到抛出参数异常 {@link IllegalArgumentException}.
     *
     * @param req {@link HttpServletRequest}
     * @param key key
     * @return value
     */
    public static String required(final HttpServletRequest req, final String key) {
        String value = req.getParameter(key);
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("Param '" + key + "' is required.");
        }
        String encoding = req.getParameter("encoding");
        return resolveValue(value, encoding);
    }

    private static String resolveValue(String value, String encoding) {
        if (StringUtils.isEmpty(encoding)) {
            encoding = StandardCharsets.UTF_8.name();
        }
        try {
            // .getBytes(StandardCharsets.UTF_8): 以UTF-8的编码取得字节
            // new String(value.getBytes(StandardCharsets.UTF_8), encoding): 以UTF-8的编码生成字符串
            // 中间通过unicode中转 （JVM内部的String，Char都是用unicode存储，没有任何编码）
            value = new String(value.getBytes(StandardCharsets.UTF_8), encoding);
        } catch (UnsupportedEncodingException ignore) {
        }
        return value.trim();
    }
}
