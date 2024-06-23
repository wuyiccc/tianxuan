package com.wuyiccc.tianxuan.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.StrPool;
import com.wuyiccc.tianxuan.auth.mapper.ChatMessageMapper;
import com.wuyiccc.tianxuan.auth.service.ChatMessageService;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import com.wuyiccc.tianxuan.pojo.ChatMessage;
import com.wuyiccc.tianxuan.pojo.netty.ChatMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wuyiccc
 * @date 2024/6/23 16:37
 */
@Slf4j
@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Resource
    private ChatMessageMapper chatMessageMapper;

    @Resource
    private RedisUtils redisUtils;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveMsg(ChatMsg chatMsg) {

        ChatMessage msg = BeanUtil.copyProperties(chatMsg, ChatMessage.class);
        // 主键id在netty中生成, 在发送的时候也能够拿到id
        msg.setId(chatMsg.getMsgId());
        msg.setShowMsgDateTimeFlag(1);

        chatMessageMapper.insert(msg);

        redisUtils.incrementHash(BaseInfoProperties.CHAT_MSG_LIST + StrPool.COLON + chatMsg.getReceiverId(), chatMsg.getSenderId(), 1);
    }
}
