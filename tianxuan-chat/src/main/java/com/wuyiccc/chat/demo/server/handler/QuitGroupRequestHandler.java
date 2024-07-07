package com.wuyiccc.chat.demo.server.handler;

import com.wuyiccc.chat.demo.protocol.request.QuitGroupRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.QuitGroupResponsePacket;
import com.wuyiccc.chat.demo.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author wuyiccc
 * @date 2024/7/7 08:34
 */
public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket requestPacket) throws Exception {

        // 1. 获取群对应的channelGroup, 然后将当前用户的channel移除
        String groupId = requestPacket.getGroupId();
        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);
        channelGroup.remove(ctx.channel());

        // 构造退群响应发送给客户端
        QuitGroupResponsePacket quitGroupResponsePacket = new QuitGroupResponsePacket();
        quitGroupResponsePacket.setGroupId(requestPacket.getGroupId());
        quitGroupResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(quitGroupResponsePacket);
    }
}
