package com.freeing.common.repsub.aspect;

import com.freeing.common.repsub.annotation.RepeatSubmit;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 重复提交 aop 功能实现
 *
 * @author yanggy
 */
@Configuration
@EnableAspectJAutoProxy
@Aspect
public class RepeatSubmitAspect {
    private static final ThreadLocal<String> KEY_CACHE = new ThreadLocal<>();

    private static final String PREFIX = "repeat_submit:";

    @Before("@annotation(repeatSubmit)")
    public void before(JoinPoint joinPoint, RepeatSubmit repeatSubmit) {
        long interval = 0;
        if (repeatSubmit.interval() > 0) {
            interval = repeatSubmit.timeUnit().toMillis(repeatSubmit.interval());
        }
        if (interval < 1000) {
            throw new IllegalArgumentException("RepeatSubmit#interval: The interval is less than 1 s.");
        }

        // 针对本次请求设置一个唯一标识（请求uri + token + args）
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        // 该操作的操作令牌
        String token = request.getHeader("token");
        if (token == null || token.isEmpty()) {
            token = request.getParameter("token");
        }
        Object[] args = joinPoint.getArgs();
        // key 由前缀 prefix + uri + token + args 组成
        String key = PREFIX + "key";
        // redis unset key 是否成功
        String result = "";
        if (result == null) {
            // 保存
        } else {
            // 重复提交
        }

    }
}
