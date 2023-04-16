package com.freeing.common.xss.filter;

import com.freeing.common.xss.utils.StrHelper;
import com.freeing.common.xss.wrapper.XssRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * xss 过滤器
 *
 * @author yanggy
 */
public class XssFilter implements Filter {
    private static final Logger log = LoggerFactory.getLogger(XssFilter.class);

    /**
     * 可放行的请求路径
     */
    private static final String IGNORE_PATH = "ignorePath";

    /**
     * 可放行的参数值
     */
    private static final String IGNORE_PARAM_VALUE = "ignoreParamValue";

    /**
     * 默认放行单点登录的登出响应(响应中包含samlp:LogoutRequest标签，直接放行)
     */
    private static final String CAS_LOGOUT_RESPONSE_TAG = "samlp:LogoutRequest";

    /**
     * 可放行的请求路径列表
     */
    private List<String> ignorePathList;

    /**
     * 可放行的参数值列表
     */
    private List<String> ignoreParamValueList;

    /**
     * 过滤器初始化方法
     */
    @Override
    public void init(FilterConfig filterConfig) {
        log.debug("XSS filter [XssFilter] init start ...");
        String ignorePaths = filterConfig.getInitParameter(IGNORE_PATH);
        String ignoreParamValues = filterConfig.getInitParameter(IGNORE_PARAM_VALUE);

        if (StrHelper.isNotBlank(ignorePaths)) {
            String[] ignorePathArr = ignorePaths.split(",");
            ignorePathList = Arrays.asList(ignorePathArr);
        }
        if (StrHelper.isNotBlank(ignoreParamValues)) {
            String[] ignoreParamValueArr = ignoreParamValues.split(",");
            ignoreParamValueList = Arrays.asList(ignoreParamValueArr);
            // 默认放行单点登录的登出响应(响应中包含samlp:LogoutRequest标签，直接放行)
            if (!ignoreParamValueList.contains(CAS_LOGOUT_RESPONSE_TAG)) {
                ignoreParamValueList.add(CAS_LOGOUT_RESPONSE_TAG);
            }
        } else {
            // 默认放行单点登录的登出响应(响应中包含samlp:LogoutRequest标签，直接放行)
            ignoreParamValueList = new ArrayList<>();
            ignoreParamValueList.add(CAS_LOGOUT_RESPONSE_TAG);
        }
        log.debug("ignorePathList= " + ignorePathList);
        log.debug("ignoreParamValueList= " + ignoreParamValueList);
        log.debug("XSS filter [XSSFilter] init end...");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        log.debug("XSS filter [XSSFilter] starting");
        // 判断uri是否包含项目名称
        String uriPath = ((HttpServletRequest) servletRequest).getRequestURI();
        if (isIgnorePath(uriPath)) {
            log.debug("ignore xss filter,path[" + uriPath + "] pass through XssFilter, go ahead...");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        } else {
            log.debug("has xss filter path[" + uriPath + "] need XssFilter, go to XssRequestWrapper");
            // 传入重写后的Request
            filterChain.doFilter(new XssRequestWrapper((HttpServletRequest) servletRequest, ignoreParamValueList),
                servletResponse);
        }
        log.debug("XSS filter [XSSFilter] stop");
    }

    @Override
    public void destroy() {
        log.debug("XSS filter [XSSFilter] destroy");
    }

    private boolean isIgnorePath(String servletPath) {
        if (StrHelper.isBlank(servletPath)) {
            return true;
        }
        if (ignorePathList == null || ignorePathList.size() < 1) {
            return false;
        } else {
            for (String ignorePath : ignorePathList) {
                if (StrHelper.isNotBlank(ignorePath) && servletPath.contains(ignorePath.trim())) {
                    return true;
                }
            }
        }
        return false;
    }
}
