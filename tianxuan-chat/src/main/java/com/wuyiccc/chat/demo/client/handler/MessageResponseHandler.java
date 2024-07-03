package com.wuyiccc.chat.demo.client.handler;

import cn.hutool.core.date.DateUtil;
import com.wuyiccc.chat.demo.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyiccc
 * @date 2024/7/3 23:07
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        System.out.println(DateUtil.date() + ": 收到服务端的消息: " + msg.getMessage());
    }
}
