package com.freeing.common.log.event;

import com.freeing.common.log.model.AuditLog;
import org.springframework.context.ApplicationEvent;

/**
 * 日志事件
 *
 * @author yanggy
 */
public class LogEvent extends ApplicationEvent {
    /**
     * 构造函数
     */
    public LogEvent(AuditLog auditLog) {
        super(auditLog);
    }
}
