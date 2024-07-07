package com.wuyiccc.chat.demo.server.handler;

import com.wuyiccc.chat.demo.protocol.request.JoinGroupRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.JoinGroupResponsePacket;
import com.wuyiccc.chat.demo.utils.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author wuyiccc
 * @date 2024/7/7 00:39
 */
@ChannelHandler.Sharable
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {

    public static final JoinGroupRequestHandler INSTANCE = new JoinGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket requestPacket) throws Exception {

        // 1. 获取群对应的channelGroup, 然后将当前用户的channel添加进去
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtils.getChannelGroup(requestPacket.getGroupId());
        channelGroup.add(ctx.channel());


        // 构造加群响应发送给客户端
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);

        ctx.channel().writeAndFlush(responsePacket);

    }
}
