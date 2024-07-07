package com.wuyiccc.chat.demo.client.handler;

import com.wuyiccc.chat.demo.protocol.response.ListGroupMembersResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyiccc
 * @date 2024/7/7 10:14
 */
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket responsePacket) throws Exception {


        System.out.println("群[" + responsePacket.getGroupId() + "] 中的人包括: " + responsePacket.getSessionList());
    }
}
