package com.freeing.common.log.listener;

import com.freeing.common.log.domain.OperationLog;
import com.freeing.common.log.event.LogEvent;
import org.springframework.context.event.EventListener;

/**
 * 操作日志的监听器，异步处理日志
 * 推荐实现 ApplicationListener<LogEvent> 接口，或者继承 BaseLogListener
 * @see BaseLogListener
 *
 * @author yanggy
 */
@Deprecated
public abstract class LogListener {

    @EventListener(LogEvent.class)
    public void saveLog(LogEvent event) {
        OperationLog operationLog = (OperationLog) event.getSource();
        this.apply(operationLog);
    }

    protected abstract void apply(OperationLog operationLog);
}
