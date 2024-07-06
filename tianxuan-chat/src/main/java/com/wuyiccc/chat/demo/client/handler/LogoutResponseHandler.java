package com.wuyiccc.chat.demo.client.handler;

import com.wuyiccc.chat.demo.protocol.response.LogoutResponsePacket;
import com.wuyiccc.chat.demo.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyiccc
 * @date 2024/7/6 22:39
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket msg) throws Exception {
        SessionUtils.unBindSession(ctx.channel());
    }
}
