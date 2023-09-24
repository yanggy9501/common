package com.freeing.log.listener;

import com.freeing.common.log.model.AuditLog;
import com.freeing.common.log.listener.BaseLogListener;
import org.springframework.stereotype.Component;


/**
 * @author yanggy
 */
@Component
public class MyLogListener extends BaseLogListener {
    @Override
    protected void apply(AuditLog auditLog) {
        System.out.println(auditLog);
    }
}
