package com.wuyiccc.chat.demo.client.handler;

import com.wuyiccc.chat.demo.protocol.response.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyiccc
 * @date 2024/7/6 22:37
 */
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket createGroupResponsePacket) throws Exception {

        System.out.println("群创建成功, id 为[" + createGroupResponsePacket.getGroupId() + "], ");
        System.out.println("群里面有: " + createGroupResponsePacket.getUserNameList());
    }
}
