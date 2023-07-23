package com.freeing.common.log.aspect;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.freeing.common.log.annotation.Log;
import com.freeing.common.log.config.OperationLogProperties;
import com.freeing.common.log.domain.OperationLog;
import com.freeing.common.log.event.LogEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * 操作日志基础切面类
 *
 * @author yanggy
 */
public abstract class BaseLogAspect implements ApplicationContextAware {
    /**
     * 排除敏感属性字段
     */
    private static String[] EXCLUDE_PROPERTIES = {"password", "oldPassword", "newPassword"};

    private ApplicationContext applicationContext;

    @Autowired
    private OperationLogProperties operationLogProperties;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @PostConstruct
    public void init() {
        String excludeProperties = operationLogProperties.getExcludeProperties();
        ArrayList<String> list = new ArrayList<>();
        if (excludeProperties != null) {
            String[] excludeFields = excludeProperties.split(",");
            for (String excludeField : excludeFields) {
                list.add(excludeField.trim());
            }
        }
        list.addAll(Arrays.asList(EXCLUDE_PROPERTIES));
        EXCLUDE_PROPERTIES = list.toArray(new String[0]);
    }

    /***
     * 定义切面
     */
    @Pointcut("@annotation(com.freeing.common.log.annotation.Log)")
    public void logPointcut() {

    }

    @Around(value = "logPointcut()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        OperationLog operationLog = new OperationLog();

        // 设置登录信息
        operationLog.setOperatorName(getOperatorName());
        operationLog.setOperatorId(getOperatorId());

        // 设置 class 信息
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 获取正在访问的类
        Class<?> executionClass = pjp.getTarget().getClass();
        operationLog.setClassPath(executionClass.getCanonicalName());
        // 获取正在访问的方法
        Method executionMethod = methodSignature.getMethod();
        // 获取访问的方法的名称
        String methodName = pjp.getSignature().getName();
        operationLog.setActionMethod(methodName);

        // 获取 @Log 注解信息
        Log annoLog = executionMethod.getAnnotation(Log.class);
        operationLog.setBusinessType(annoLog.businessType().getType());
        operationLog.setDescription(annoLog.description());
        operationLog.setModule(annoLog.module());

        // 设置请求 Request 信息
        if (getRequest() != null) {
            operationLog.setHttpMethod(getRequest().getMethod());
            operationLog.setRequestUri(getRequest().getRequestURI());
            operationLog.setRequestIp(getIpAddr(getRequest()));
        }

        Object[] args = null;
        if (annoLog.enableSaveParma()) {
            // 获取访问的方法的参数
            args = pjp.getArgs();
            if (args != null && args.length > 0) {
                operationLog.setParams(subString(JSONArray.toJSONString(args, excludePropertyFilter()), 2048));
            }
        }

        long startNanos = System.nanoTime();
        operationLog.setStartTime(new Date(System.currentTimeMillis()));

        Object result;
        try {
            //让代理方法执行
            beforeProceed(operationLog, args);
            result = pjp.proceed();
            if (annoLog.enableSaveResult()) {
                // 获取访问的方法的请求参数
                if (result != null) {
                    operationLog.setResult(subString(JSONArray.toJSONString(result, excludePropertyFilter()), 2048));
                }
            }
            afterProceed(operationLog, result);
            operationLog.setStatus("1");
        } catch (Throwable ex) {
            // 设置异常信息
            operationLog.setStatus("0");
            operationLog.setExDesc(subString(ex.getMessage(), 2048));
            operationLog.setExDetail(subString(ex.toString(), 2048));
            // 需要抛出异常，全局异常处理可能还需要进行处理
            throw ex;
        } finally {
            // 设置耗时
            long endNanos = System.nanoTime();
            operationLog.setElapsedTime(formatNnanos(endNanos - startNanos));
            operationLog.setEndTime(new Date(System.currentTimeMillis()));

            // 发布事件，日志交给监听者处理
            applicationContext.publishEvent(new LogEvent(operationLog));
        }
        return result;
    }

    /**
     * 目标方法执行前
     *
     * @param operationLog 日志实体类
     * @param args 目标方法参数
     */
    protected void beforeProceed(OperationLog operationLog, Object[] args) {

    }

    /**
     * 目标方法之后
     *
     * @param operationLog 日志实体类
     * @param result 目标方法返回结果
     */
    private void afterProceed(OperationLog operationLog, Object result) {

    }

    /**
     * 获取操作人
     *
     * @return
     */
    protected abstract String getOperatorName();

    /**
     * 获取操作人 ID
     *
     * @return
     */
    protected abstract String getOperatorId();

    private static String subString(String str, int maxLength) {
        if (str == null) {
            return null;
        }
        return str.length() > maxLength ? str.substring(0, maxLength) : str;
    }

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            return requestAttributes.getRequest();
        }
        return null;

    }

    private static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException ignored) {
                    return null;
                }
                ipAddress = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP, 多个IP按照','分割
        // "***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    private static String formatNnanos(long nanos) {
        TimeUnit unit = chooseUnit(nanos);
        double value = (double)nanos / (double)TimeUnit.NANOSECONDS.convert(1L, unit);
        return formatCompact4Digits(value) + " " + abbreviate(unit);
    }

    private static String formatCompact4Digits(double value) {
        return String.format(Locale.ROOT, "%.4g", value);
    }

    private static TimeUnit chooseUnit(long nanos) {
        if (TimeUnit.DAYS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.DAYS;
        } else if (TimeUnit.HOURS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.HOURS;
        } else if (TimeUnit.MINUTES.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.MINUTES;
        } else if (TimeUnit.SECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.SECONDS;
        } else if (TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.MILLISECONDS;
        } else {
            return TimeUnit.MICROSECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L
                ? TimeUnit.MICROSECONDS : TimeUnit.NANOSECONDS;
        }
    }

    private static String abbreviate(TimeUnit unit) {
        switch (unit) {
            case NANOSECONDS:
                return "ns";
            case MICROSECONDS:
                return "μs";
            case MILLISECONDS:
                return "ms";
            case SECONDS:
                return "s";
            case MINUTES:
                return "min";
            case HOURS:
                return "h";
            case DAYS:
                return "d";
            default:
                throw new AssertionError();
        }
    }

    /**
     * 忽略敏感属性
     */
    public PropertyPreFilters.MySimplePropertyPreFilter excludePropertyFilter() {
        return new PropertyPreFilters().addFilter().addExcludes(EXCLUDE_PROPERTIES);
    }
}