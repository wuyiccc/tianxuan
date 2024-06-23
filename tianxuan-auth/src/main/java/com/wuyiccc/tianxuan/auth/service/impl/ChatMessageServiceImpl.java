package com.wuyiccc.tianxuan.auth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.text.StrPool;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageHelper;
import com.wuyiccc.tianxuan.auth.mapper.ChatMessageMapper;
import com.wuyiccc.tianxuan.auth.service.ChatMessageService;
import com.wuyiccc.tianxuan.common.base.BaseInfoProperties;
import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.common.util.RedisUtils;
import com.wuyiccc.tianxuan.pojo.ChatMessage;
import com.wuyiccc.tianxuan.pojo.netty.ChatMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;

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

    @Override
    public PagedGridResult list(String senderId, String receiverId, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        LambdaQueryWrapper<ChatMessage> wrapper = Wrappers.lambdaQuery();

        wrapper.or(
                qw -> qw.eq(ChatMessage::getSenderId, senderId).eq(ChatMessage::getReceiverId, receiverId)
        );

        wrapper.or(
                qw -> qw.eq(ChatMessage::getSenderId, receiverId).eq(ChatMessage::getReceiverId, senderId)
        );
        wrapper.orderByDesc(ChatMessage::getChatTime);

        List<ChatMessage> list = chatMessageMapper.selectList(wrapper);

        // 聊天记录是展现最新的数据在最下方, 所以这里需要再次逆序, 把最近的消息放在最后一条
        list.sort(Comparator.comparing(ChatMessage::getChatTime));

        return PagedGridResult.build(list, page);
    }
}
