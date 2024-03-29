package com.freeing.common.support.pubsub;

/**
 * 发布者接口，发布 #getMessageType 方法返回类型的消息
 *
 * @author yanggy
 */
public interface IPublisher {
    /**
     * 获取消息类型
     *
     * @return 获取消息类型
     */
    String getMessageType();

    /**
     * 	生产消息 （同步处理）
     *
     * @param message	消息实体
     */
    default void syncPublishMessage(Message message){
        if (getMessageType() == null){
            throw new IllegalArgumentException("Message type can not be null");
        }
        SubscribePublish.getInstance().syncPublishMessage(getMessageType(), message);
    }

    /**
     * 生产消息 （异步处理）
     *
     * @param message 消息实体
     */
    default void asyncPublishMessage(Message message){
        if (getMessageType() == null){
            throw new IllegalArgumentException("Message type can not be null");
        }
        SubscribePublish.getInstance().asyncPublishMessage(getMessageType(), message);
    }
}


