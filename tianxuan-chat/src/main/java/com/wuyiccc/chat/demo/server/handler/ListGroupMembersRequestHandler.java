package com.wuyiccc.chat.demo.server.handler;

import com.wuyiccc.chat.demo.protocol.request.ListGroupMembersRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.ListGroupMembersResponsePacket;
import com.wuyiccc.chat.demo.session.Session;
import com.wuyiccc.chat.demo.utils.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/7/7 09:57
 */
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket listGroupMembersRequestPacket) throws Exception {

        String groupId = listGroupMembersRequestPacket.getGroupId();

        ChannelGroup channelGroup = SessionUtils.getChannelGroup(groupId);

        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();
        responsePacket.setGroupId(groupId);

        List<Session> sessionList = new ArrayList<>();

        for (Channel channel : channelGroup) {

            Session session = SessionUtils.getSession(channel);
            sessionList.add(session);
        }

        responsePacket.setSessionList(sessionList);

        ctx.channel().writeAndFlush(responsePacket);
    }
}
