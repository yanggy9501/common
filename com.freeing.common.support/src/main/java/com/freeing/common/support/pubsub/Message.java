package com.freeing.common.support.pubsub;

import java.util.Map;

/**
 * 发布订阅的消息
 *
 * @author yanggy
 */
public class Message {
    /**
     * 消息类型
     */
    private String messageType;

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

    public Message() {

    }

    public Message(String messageType, Object data) {
        this(messageType, null, data, null);
    }

    public Message(String messageType, String tag, Object data, Map<String, Object> extra) {
        this.messageType = messageType;
        this.tag = tag;
        this.data = data;
        this.extra = extra;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
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
        return "Message{" +
            "messageType='" + messageType + '\'' +
            ", tag='" + tag + '\'' +
            ", data=" + data +
            ", extra=" + extra +
            '}';
    }
}
