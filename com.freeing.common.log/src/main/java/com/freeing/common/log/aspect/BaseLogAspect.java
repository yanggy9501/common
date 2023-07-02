package com.freeing.common.log.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freeing.common.log.annotation.Log;
import com.freeing.common.log.domain.OperationLog;
import com.google.common.base.Stopwatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 操作日志使用 spring event异步入库
 *
 * @author yanggy
 */
public class BaseLogAspect {
    /**
     * 排除敏感属性字段
     */
    public static final String[] EXCLUDE_PROPERTIES = { "password", "oldPassword", "newPassword", "confirmPassword",
        "username", "phone", "mobilePhone", "identityCard"};

    @Autowired
    private ObjectMapper jsonUtil;

    /***
     * 定义切面
     */
    @Pointcut("@annotation(com.freeing.common.log.annotation.Log)")
    public void logPointcut() {

    }

    @Around(value = "logPointcut()")
    public Object logAround(ProceedingJoinPoint pjp) throws JsonProcessingException {
        OperationLog operationLog = new OperationLog();

        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 获取正在访问的类
        Class<?> executionClass = pjp.getTarget().getClass();
        operationLog.setClassPath(executionClass.getCanonicalName());
        // 获取正在访问的方法
        Method executionMethod = methodSignature.getMethod();
        // 获取访问的方法的名称
        String methodName = pjp.getSignature().getName();
        operationLog.setActionMethod(methodName);
        // 获取 @Log
        Log annoLog = executionMethod.getAnnotation(Log.class);
        if (annoLog.saveParmas()) {
            // 获取访问的方法的参数
            Object[] args = pjp.getArgs();
            if (args != null) {
                operationLog.setParams(jsonUtil.writeValueAsString(args));
            }
        }

        Stopwatch stopwatch = null;
        if (annoLog.saveConsumingTime()) {
            stopwatch = Stopwatch.createStarted();
            operationLog.setStartTime(new Date(System.currentTimeMillis()));
        }
        Object result = null;
        try {
            //让代理方法执行
            result = pjp.proceed();
            if (annoLog.saveConsumingTime()) {
                assert stopwatch != null;
                stopwatch.stop();
                operationLog.setConsumingTime(stopwatch.toString());
                operationLog.setFinishTime(new Date(System.currentTimeMillis()));
            }
        } catch (Throwable e) {
            // 3.异常通知
        } finally {
            // 4.最终通知
        }
        return result;
    }

    /**
     * 获取用户名
     *
     * @return
     */
    protected String getUsername() {
        return "";
    }


    /**
     * 日志处理
     *
     * @param joinPoint 切入点
     * @param annoLog 操作注解
     * @param e 异常对象
     * @param result
     */
    protected void handleLog(JoinPoint joinPoint, Log annoLog, Object result, Exception e) {

        OperationLog operationLog = new OperationLog();

        // 设置操作状态
        // operationLog.setStatus(BusinessStatus.SUCCESS.getCode());
        // 设置请求地址，如: /user/1
        // operationLog.setRequestUri(getUri());
        // // 请求ip
        // operationLog.setRequestIp(getRequestIp());
        // // 设置操作人
        // operationLog.setUsername(getUsername());

        // // 处理请求参数
        // handleRequestParams(joinPoint, operationLog, annoLog);
        //
        // // 设置响应参数和值
        // if (annoLog.isSaveResponseDate() && result != null) {
        //     operationLog.setJsonResult(JSON.toJSONString(result));
        // }
        //
        // // 发送操作日志消息
        // rabbitTemplate.convertAndSend(RabbitConfig.LOG_TOPIC_EXCHANGE,
        //     RoutingKeyConstants.OPERATION_LOG_BINDING_LOG_EXCHANGE,
        //     JSON.toJSONString(operationLog));
    }

    /**
     * 处理请求参数
     *
     * @param joinPoint 连接点
     * @param operationLog 日志对象
     * @param annoLog 日志注解
     */
    // public void handleRequestParams(JoinPoint joinPoint,SysOperationLog operationLog, Log annoLog) {
    //     // 设置方法名称
    //     String className = joinPoint.getSignature().getDeclaringTypeName();
    //     String methodName = joinPoint.getSignature().getName();
    //     operationLog.setMethod(className + "." + methodName + "()");
    //     // 设置请求方式
    //     operationLog.setRequestMethod(ServletUtils.getRequest().getMethod());
    //     // 处理Log注解的参数
    //     operationLog.setBusinessType(annoLog.businessType().getCode());
    //     // 设置标题（操作模块）
    //     operationLog.setTitle(annoLog.title());
    //     // 设置操作人类别
    //     operationLog.setOperatorType(annoLog.operatorType().ordinal());
    //     // 设置请求参数
    //     if (annoLog.isSaveRequestData()) {
    //         setRequestValue(operationLog, joinPoint);
    //     }
    // }

    /**
     *
     *
     * @param operationLog 日志对象
     * @param joinPoint 连接点
     */
    // public void setRequestValue(SysOperationLog operationLog, JoinPoint joinPoint) {
    //     // 请求对象获取请求参数
    //     Map<String, String[]> parameterMap = ServletUtils.getRequest().getParameterMap();
    //     if (parameterMap != null && parameterMap.size() > 0) {
    //         String paramsStr = JSON.toJSONString(parameterMap, excludePropertyFilter());
    //         operationLog.setOperParam(paramsStr);
    //     } else {
    //         // request获取不到，从joinPoint连接点获取参数
    //         Object[] args = joinPoint.getArgs();
    //         if (args != null && args.length > 0) {
    //             String paramsStr = JSONArray.toJSONString(args);
    //             operationLog.setOperParam(paramsStr);
    //         }
    //     }
    // }

}