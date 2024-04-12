package com.freeing.common.mq;

import com.freeing.common.mq.model.MQMessage;

/**
 * 消息发送接口
 */
public interface MessageSenderService {

    /**
     * 发送消息
     *
     * @param message 消息模型
     * @return true|false
     */
    boolean send(MQMessage<?> message);
}
