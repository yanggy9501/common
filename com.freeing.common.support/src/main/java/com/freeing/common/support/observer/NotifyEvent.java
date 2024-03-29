package com.freeing.common.support.observer;

import java.util.Map;

/**
 * 观察者模式的通知事件
 *
 * @author yanggy
 */
public class NotifyEvent {
    /**
     * 通知事件，一级分类
     */
    private String event;

    /**
     * 标签，二级分类
     */
    private String tag;

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

    public NotifyEvent(String event, Object data) {
        this(event, null, data, null);
    }

    public NotifyEvent(String event, String tag, Object data, Map<String, Object> extra) {
        this.event = event;
        this.tag = tag;
        this.data = data;
        this.extra = extra;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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
