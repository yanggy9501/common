package com.freeing.common.web.util;


import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * web utils
 *
 * @author yanggy
 */
public class WebUtils {
    /**
     * 获取请求头
     *
     * @param request HttpServletRequest
     * @return request header
     */
    public static Map<String, String> getHeaders(HttpServletRequest request) {
        Map<String, String> map = new LinkedHashMap<>();
        Enumeration<String> enumeration = request.getHeaderNames();
        String encoding = request.getParameter("encoding");
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement();
                String value = request.getHeader(key);
                map.put(key, resolveValue(value, encoding));
            }
        }
        return map;
    }

    /**
     * 从 parameterMap 获取目标值，如果找不到，将返回默认值。
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
     * 从 parameterMap 获取目标值，如果找不到抛出参数异常 {@link IllegalArgumentException}.
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
