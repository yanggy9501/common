package com.freeing.common.support.pubsub;

/**
 * 订阅者接口
 */
public interface ISubscriber {
    /**
     * 获取此订阅对象需求的消息类型，需要子类实现
     *
     * @return 消息类型
     */
    String getMessageType();


    /**
     * 订阅
     */
    default void subcribe() {
        SubscribePublish.getInstance().subscribe(this);
    }

    /**
     * 取消订阅
     */
    default void unSubcribe(){
        SubscribePublish.getInstance().unSubscribe(this);
    }

    /**
     * 接收消息并处理
     *
     * @param message 消息实例
     */
    void receiveMessage(Object message);
}
