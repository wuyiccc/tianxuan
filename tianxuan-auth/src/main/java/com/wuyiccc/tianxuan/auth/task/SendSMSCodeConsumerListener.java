package com.wuyiccc.tianxuan.auth.task;

import cn.hutool.json.JSONUtil;
import com.wuyiccc.tianxuan.common.constant.MQConstants;
import com.wuyiccc.tianxuan.common.util.DingDingMsgUtils;
import com.wuyiccc.tianxuan.pojo.dto.SmsCodeDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/9/4 10:45
 */
@Slf4j
public class SendSMSCodeConsumerListener implements MessageListenerConcurrently {


    @Resource
    private DingDingMsgUtils dingDingMsgUtils;

    public SendSMSCodeConsumerListener(DingDingMsgUtils dingDingMsgUtils) {
        this.dingDingMsgUtils = dingDingMsgUtils;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messageExtList, ConsumeConcurrentlyContext context) {
        try {
            log.info("{} mq获取数据条数: {}", MQConstants.TOPIC_SMS_CODE, messageExtList.size());
            for (MessageExt messageExt : messageExtList) {
                byte[] body = messageExt.getBody();
                SmsCodeDTO smsCodeDTO = JSONUtil.toBean(new String(body), SmsCodeDTO.class);
                dingDingMsgUtils.sendSMSCode(smsCodeDTO.getSmsCode());
            }

        } catch (Exception e) {
            log.error("rent_sync_redo_binlog 消费失败", e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
