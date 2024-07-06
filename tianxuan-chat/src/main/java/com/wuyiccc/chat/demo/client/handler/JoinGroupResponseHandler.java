package com.wuyiccc.chat.demo.client.handler;

import com.wuyiccc.chat.demo.protocol.console.impl.JoinGroupConsoleCommand;
import com.wuyiccc.chat.demo.protocol.response.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyiccc
 * @date 2024/7/6 23:59
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket msg) throws Exception {

        if (msg.isSuccess()) {
            System.out.println("加入群[ " + msg.getGroupId() + "] 成功!");
        } else {
            System.err.println("加入群[ " + msg.getGroupId() + "] 失败, 原因为: " + msg.getReason());
        }
    }
}
