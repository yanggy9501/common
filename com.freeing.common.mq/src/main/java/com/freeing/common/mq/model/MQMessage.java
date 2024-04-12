package com.freeing.common.mq.model;

/**
 * 消息模型
 */
public abstract class MQMessage<T> {
    /**
     * 消息目的地，如：rocketmq，kafka主题
     */
    private String destination;

    private T message;

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
