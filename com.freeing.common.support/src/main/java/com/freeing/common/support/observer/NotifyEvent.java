package com.freeing.common.support.observer;

import java.util.Map;

/**
 * 观察者模式的通知事件
 *
 * @author yanggy
 */
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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    @Override
    public String toString() {
        return "NotifyEvent{" +
            "event='" + event + '\'' +
            ", data=" + data +
            ", extra=" + extra +
            '}';
    }
}
