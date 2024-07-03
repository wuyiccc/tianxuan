package com.wuyiccc.chat.demo.server.handler;

import cn.hutool.core.date.DateUtil;
import com.wuyiccc.chat.demo.protocol.request.MessageRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyiccc
 * @date 2024/7/3 22:56
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {

        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        System.out.println(DateUtil.date() + ": 收到客户端消息: " + msg.getMessage());
        messageResponsePacket.setMessage("服务端回复 [" + msg.getMessage() + "]");

        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
