package com.freeing.common.support.observer;

import lombok.Data;

import java.util.Map;

/**
 * 观察者模式的通知事件
 *
 * @author yanggy
 */
@Data
public class NotifyEvent {
    /**
     * 通知事件
     */
    private String event;

    /**
     * data
     */
    private Object data;

    /**
     * 额外参数
     */
    private Map<String, Object> extra;

    public NotifyEvent() {

    }

    public NotifyEvent(String event, Map<String, Object> extra) {
        this.event = event;
        this.extra = extra;
    }
}
