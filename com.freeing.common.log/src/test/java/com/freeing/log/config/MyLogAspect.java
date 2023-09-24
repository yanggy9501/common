package com.freeing.log.config;

import com.freeing.common.log.aspect.BaseLogAspect;
import com.freeing.common.log.model.AuditLog;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author yanggy
 */
@Aspect
@Component
public class MyLogAspect extends BaseLogAspect {

    @Override
    protected void beforeProceed(AuditLog auditLog) {
        auditLog.setOperatorId("id:00000");
        auditLog.setOperatorName("name:00000");
        System.out.println("扩展：目标方法执行之前");
    }

    @Override
    protected void afterProceed(AuditLog auditLog) {
        System.out.println("扩展：目标方法执行之后");
    }
}
