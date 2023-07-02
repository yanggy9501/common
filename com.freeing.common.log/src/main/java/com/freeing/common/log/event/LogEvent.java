package com.freeing.common.log.event;

import com.freeing.common.log.domain.OperationLog;
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
    public LogEvent(OperationLog operationLog) {
        super(operationLog);
    }
}
