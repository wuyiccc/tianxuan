package com.wuyiccc.tianxuan.auth.service;

import com.wuyiccc.tianxuan.common.result.PagedGridResult;
import com.wuyiccc.tianxuan.pojo.netty.ChatMsg;

/**
 * @author wuyiccc
 * @date 2024/6/23 16:37
 */
public interface ChatMessageService {


    public void saveMsg(ChatMsg chatMsg);

    PagedGridResult list(String senderId, String receiverId, Integer page, Integer pageSize);

    void updateMsgSignRead(String msgId);
}
