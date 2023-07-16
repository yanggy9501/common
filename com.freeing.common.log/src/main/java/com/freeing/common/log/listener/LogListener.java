package com.freeing.common.log.listener;

import com.freeing.common.log.domain.OperationLog;
import com.freeing.common.log.event.LogEvent;
import org.springframework.context.event.EventListener;

import java.util.function.Consumer;

/**
 * 操作日志的监听器，异步处理日志
 * ps: 外部引用时需要配置 consumer 并且交给 spring 管理
 *
 * @author yanggy
 */
public class LogListener {
    /**
     * 消费者函数，日记记录的工作交给lambda表达式来完成
     */
    private final Consumer<OperationLog> consumer;

    public LogListener(Consumer<OperationLog> consumer) {
        this.consumer = consumer;
    }

    @EventListener(LogEvent.class)
    public void saveLog(LogEvent event) {
        OperationLog optLog = (OperationLog) event.getSource();
        consumer.accept(optLog);
    }
}
