package com.wuyiccc.chat.demo.server.handler;

import com.wuyiccc.chat.demo.protocol.request.HeartBeatRequestPacket;
import com.wuyiccc.chat.demo.protocol.response.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author wuyiccc
 * @date 2024/7/7 17:14
 */
@ChannelHandler.Sharable
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {

    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();


    private HeartBeatRequestHandler() {

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket msg) throws Exception {

        System.out.println("检测到客户端心跳: " + Thread.currentThread().getName());
        ctx.writeAndFlush(msg);
    }


}
