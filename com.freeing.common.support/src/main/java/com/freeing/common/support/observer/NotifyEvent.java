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
     * 其他参数
     */
    private Map<String, Object> extra;
}
