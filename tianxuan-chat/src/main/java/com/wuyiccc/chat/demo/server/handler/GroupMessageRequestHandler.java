package com.wuyiccc.chat.demo.server.handler;

import com.wuyiccc.chat.demo.protocol.request.GroupMessageRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.GroupMessageResponsePacket;
import com.wuyiccc.chat.demo.session.Session;
import com.wuyiccc.chat.demo.utils.SessionUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * @author wuyiccc
 * @date 2024/7/7 10:40
 */
@ChannelHandler.Sharable
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {

    public static final GroupMessageRequestHandler INSTANCE = new GroupMessageRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket requestPacket) throws Exception {


        System.out.println("接受到群发消息: " + requestPacket.getMessage());
        // 1. 拿到消息
        String toGroupId = requestPacket.getToGroupId();


        ChannelGroup channelGroup = SessionUtils.getChannelGroup(toGroupId);

        GroupMessageResponsePacket groupMessageResponsePacket = new GroupMessageResponsePacket();
        groupMessageResponsePacket.setFromGroupId(toGroupId);
        groupMessageResponsePacket.setMessage(requestPacket.getMessage());

        // 拿到发送人的信息
        Session sendSession = SessionUtils.getSession(ctx.channel());
        groupMessageResponsePacket.setFromUser(sendSession);

        channelGroup.writeAndFlush(groupMessageResponsePacket);
    }
}
