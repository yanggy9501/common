package com.freeing.common.web.aspect;

import com.freeing.common.web.annotation.RepeatSubmit;
import com.freeing.common.web.util.ServletUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

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
            // TODO 抛出异常，重复提交的时间间隔 interval 必须大于 1 秒
        }

        // 针对本次请求设置一个唯一标识（请求uri + token + args）
        HttpServletRequest request = ServletUtils.getRequest();
        String uri = request.getRequestURI();
        String token = request.getHeader("token");

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
