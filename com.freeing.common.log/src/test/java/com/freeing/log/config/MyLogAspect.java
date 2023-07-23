package com.freeing.log.config;

import com.freeing.common.log.aspect.BaseLogAspect;
import com.freeing.common.log.domain.OperationLog;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author yanggy
 */
@Aspect
@Component
public class MyLogAspect extends BaseLogAspect {
    @Override
    protected String getOperatorName() {
        return "Operator";
    }

    @Override
    protected String getOperatorId() {
        return "id:00000";
    }

    @Override
    protected void beforeProceed(OperationLog operationLog, Object[] args) {
        System.out.println("扩展：目标方法执行之前");
    }

    @Override
    protected void afterProceed(OperationLog operationLog, Object result) {
        System.out.println("扩展：目标方法执行之后");
    }
}
