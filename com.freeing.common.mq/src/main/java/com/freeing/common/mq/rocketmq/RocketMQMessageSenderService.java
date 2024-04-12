package com.freeing.common.mq.rocketmq;


import com.freeing.common.mq.MessageSenderService;
import com.freeing.common.mq.model.MQMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "message.mq.type", havingValue = "rocketmq")
public class RocketMQMessageSenderService implements MessageSenderService {


    @Override
    public boolean send(MQMessage<?> message) {
        return true;
    }
}
