package com.wuyiccc.tianxuan.auth.task;

import com.wuyiccc.tianxuan.common.constant.MQConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/6/23 16:27
 */
@Slf4j
public class ChatMgsConsumerListener implements MessageListenerOrderly {
    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeOrderlyContext context) {
        try {
            log.info("{} mq获取数据条数: {}", MQConstants.TOPIC_CHAT, messageExtList.size());
            for (MessageExt messageExt : messageExtList) {
                byte[] body = messageExt.getBody();

                log.info("聊天记录消费: {}", new String(body));
            }

        } catch (Exception e) {
            log.error("rent_sync_redo_binlog 消费失败", e);
            return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }
}
