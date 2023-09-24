package com.freeing.common.log.listener;

import com.freeing.common.log.model.AuditLog;
import com.freeing.common.log.event.LogEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author yanggy
 */
public abstract class BaseLogListener implements ApplicationListener<LogEvent> {
    @Override
    public void onApplicationEvent(LogEvent event) {
        apply((AuditLog)event.getSource());
    }

    protected abstract void apply(AuditLog auditLog);
}
