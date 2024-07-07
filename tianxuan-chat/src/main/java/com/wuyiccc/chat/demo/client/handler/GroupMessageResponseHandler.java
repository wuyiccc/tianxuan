package com.wuyiccc.chat.demo.client.handler;

import com.wuyiccc.chat.demo.protocol.response.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyiccc
 * @date 2024/7/7 10:58
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket groupMessageResponsePacket) throws Exception {


        System.out.println("接收到群发消息, 群组id: " + groupMessageResponsePacket.getFromGroupId() + ", 消息发送人: " + groupMessageResponsePacket.getFromUser()
         + ", 消息内容: " + groupMessageResponsePacket.getMessage());
    }
}
