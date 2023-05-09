package com.freeing.common.xss.util;

import org.owasp.validator.html.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * XSS工具类， 用于过滤特殊字符
 *
 * @author yanggy
 */
public class XssUtils {
    private static final Logger log = LoggerFactory.getLogger(XssUtils.class);

    /**
     * 配置文件
     */
    private static final String ANTISAMY_SLASHDOT_XML = "antisamy-slashdot.xml";

    /**
     * 过滤策略
     */
    private static Policy policy;

    static {
        log.debug(" start read XSS configuration file [{}]", ANTISAMY_SLASHDOT_XML);
        InputStream inputStream = XssUtils.class.getClassLoader().getResourceAsStream(ANTISAMY_SLASHDOT_XML);
        try {
            policy = Policy.getInstance(inputStream);
            log.debug("read XSS configuration file [{}] success", ANTISAMY_SLASHDOT_XML);
        } catch (PolicyException e) {
            log.error("read XSS configuration file [{}] fail , reason:{}", ANTISAMY_SLASHDOT_XML, e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    log.error("read XSS configuration file [{}] fail , reason:{}", ANTISAMY_SLASHDOT_XML, e.getMessage());
                }
            }
        }
    }

    /**
     * 跨站攻击语句过滤方法
     *
     * @param paramValue 待过滤的参数
     * @param ignoreParamValueList 忽略过滤的参数列表
     * @return String
     */
    public static String xssClean(String paramValue, List<String> ignoreParamValueList) {
        AntiSamy antiSamy = new AntiSamy();
        try {
            log.debug("raw value before xssClean: {}", paramValue);
            // 不需要过滤
            if (isIgnoreParamValue(paramValue, ignoreParamValueList)) {
                log.debug("ignore the xssClean, keep the raw paramValue: {} " ,paramValue);
                return paramValue;
            } else {
                final CleanResults cr = antiSamy.scan(paramValue, policy);
                cr.getErrorMessages().forEach(log::debug);
                String str = cr.getCleanHTML();
                log.debug("xss filter value after xssClean {}", str);
                return str;
            }
        } catch (ScanException e) {
            log.error("scan failed parameter is is [{}] ;error: {}", paramValue, e.getMessage());
        } catch (PolicyException e) {
            log.error("antisamy convert failed  parameter is [{}] ;error: {}", paramValue, e.getMessage());
        }
        return paramValue;
    }

    /**
     * 判断该参数值是否需要过滤
     *
     * @param paramValue 参数
     * @param ignoreParamValueList 忽略列表
     * @return boolean
     */
    private static boolean isIgnoreParamValue(String paramValue, List<String> ignoreParamValueList) {
        if (StrHelper.isBlank(paramValue)) {
            return true;
        }
        if (ignoreParamValueList != null && ignoreParamValueList.size() > 0) {
            for (String ignoreParamValue : ignoreParamValueList) {
                if (paramValue.contains(ignoreParamValue)) {
                    return true;
                }
            }
        }
        return false;
    }
}
