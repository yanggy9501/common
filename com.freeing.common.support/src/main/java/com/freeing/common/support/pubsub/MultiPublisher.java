package com.freeing.common.support.pubsub;

/**
 * 发布者接口
 *
 * @author yanggy
 */
public interface MultiPublisher {
    /**
     * 	生产消息 （同步处理）
     *
     * @param messageType 消息类型
     * @param message	消息实体
     */
    default void syncPublishMessage(String messageType, Message message){
        if (messageType == null){
            throw new IllegalArgumentException("Message type can not be null");
        }
        SubscribePublish.getInstance().syncPublishMessage(messageType, message);
    }

    /**
     * 生产消息 （异步处理）
     *
     * @param messageType 消息类型
     * @param message 消息实体
     */
    default void asyncPublishMessage(String messageType, Message message){
        if (messageType == null){
            throw new IllegalArgumentException("Message type can not be null");
        }
        SubscribePublish.getInstance().asyncPublishMessage(messageType, message);
    }
}


